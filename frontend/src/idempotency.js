export async function caclHash(obj) {
    const data = new TextEncoder().encode(JSON.stringify(obj));
    const digest = await crypto.subtle.digest("SHA-256", data);
    return Array.from(new Uint8Array(digest))
                .map(b => b.toString(16).padStart(2, "0"))
                .join("");
}

export async function getOrCreateIdempotencyKey(payload) {
    const hash = await caclHash(payload);
    const existing = localStorage.getItem(`idem:${hash}`);
    if (existing) return existing;

    const fresh = crypto.randomUUID();
    localStorage.setItem(`idem:${hash}`, fresh);
    return fresh;
}
