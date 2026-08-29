// Small shared helpers used by every page.
// All requests are relative, so this must be served by the same
// Spring Boot app that exposes /api/... (static/ folder on the same origin).

// Turns raw backend/network error text into a readable Swedish message.
// Returns null when the text doesn't match a known pattern, so the
// original message can still be shown for things like validation errors.
function friendlyErrorMessage(rawMessage) {
  const msg = (rawMessage || "").toLowerCase();

  if (
    msg.includes("connection refused") ||
    msg.includes("i/o error") ||
    msg.includes("econnrefused") ||
    msg.includes("unknownhostexception") ||
    msg.includes("connect timed out")
  ) {
    return "Kan inte nå databasen eller en av mikrotjänsterna just nu. Kontrollera att alla tjänster körs och försök igen.";
  }

  if (msg.includes("failed to fetch") || msg.includes("networkerror") || msg.includes("load failed")) {
    return "Kan inte nå servern. Kontrollera att applikationen körs och att du har anslutning.";
  }

  return null;
}

const Api = {
  async request(url, options = {}) {
    let res;
    try {
      res = await fetch(url, options);
    } catch (networkErr) {
      const err = new Error(friendlyErrorMessage(networkErr.message) || "Kan inte nå servern. Kontrollera att applikationen körs.");
      err.status = 0;
      throw err;
    }

    const contentType = res.headers.get("content-type") || "";
    const isJson = contentType.includes("application/json");
    const body = isJson ? await res.json().catch(() => null) : await res.text();

    if (!res.ok) {
      const rawMessage =
        (isJson && body && (body.message || body.error)) ||
        (typeof body === "string" && body) ||
        `Anropet misslyckades (status ${res.status}).`;
      const err = new Error(friendlyErrorMessage(rawMessage) || rawMessage);
      err.status = res.status;
      err.rawMessage = rawMessage;
      throw err;
    }
    return body;
  },

  get(url) {
    return this.request(url);
  },

  postJson(url, data) {
    return this.request(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });
  },

  putJson(url, data) {
    return this.request(url, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });
  },

  // For endpoints declared with @RequestParam (not @RequestBody),
  // the values must go in the query string, not a JSON body.
  post(url, params) {
    const qs = new URLSearchParams(params).toString();
    return this.request(`${url}?${qs}`, { method: "POST" });
  },

  put(url, params) {
    const qs = new URLSearchParams(params).toString();
    return this.request(`${url}?${qs}`, { method: "PUT" });
  },

  delete(url) {
    return this.request(url, { method: "DELETE" });
  },
};

function showAlert(container, message, type = "error") {
  container.innerHTML = "";
  if (!message) return;
  const div = document.createElement("div");
  div.className = `alert alert-${type}`;
  div.textContent = message;
  container.appendChild(div);
}

function qs(name) {
  return new URLSearchParams(window.location.search).get(name);
}

function escapeHtml(str) {
  const div = document.createElement("div");
  div.textContent = str ?? "";
  return div.innerHTML;
}

// Highlight the current page in the nav bar.
document.addEventListener("DOMContentLoaded", () => {
  const path = window.location.pathname.replace(/\/$/, "") || "/";
  document.querySelectorAll("nav a[data-nav]").forEach((a) => {
    const target = a.getAttribute("href").replace(/\/$/, "") || "/";
    if (target === path) a.classList.add("active");
  });
});
