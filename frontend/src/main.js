import { fetchWithResilience } from "./http.js";
import { getOrCreateIdempotencyKey } from "./idempotency.js";

const submitBtn = document.getElementById("submit");
const result = document.getElementById("result");
const banner = document.getElementById("banner");

let failures = 0;
const MAX_FAIL = 3;

submitBtn.onclick = async () => {
    const name = document.getElementById("name").value;

    const payload = { name: name };
    const idemKey = await getOrCreateIdempotencyKey(payload);

    try {
        const res = await fetchWithResilience("http://localhost:8080/orders/create", {
            method: "POST",
            body: JSON.stringify(payload),
            idempotencyKey: idemKey,
            retry: { retries: 3, baseDelayMs: 300, timeoutMs: 3500, jitter: true }
        });

        const data = await res.json();
        result.innerText = JSON.stringify(data, null, 2);

        failures = 0;
        banner.style.display = "none";
        submitBtn.disabled = false;

    } catch (e) {
        failures++;

        if (failures >= MAX_FAIL) {
            banner.style.display = "block";
            submitBtn.disabled = true;
        }

        result.innerText = "Error: " + e.message;
    }
};