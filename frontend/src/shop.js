// Не функціонуватиме, бо сервер за замовчуванням перенаправляє на login

import {getOrCreateIdempotencyKey} from "./idempotency.js";
import {fetchWithResilience} from "./http.js";

const itemTable = document.getElementById('item-table')
const banner = document.getElementById("banner");
const goldCounterView = document.getElementById('gold')

let gold = 100000;

async function loadData() {
    try {
        const shopRes = await fetch('http://localhost:8080/api/shop');
        const items = await shopRes.json();

        goldCounterView.textContent = gold.toString();

        const body = document.getElementById('item-table-body');
        body.innerHTML = '';

        if (items.length === 0) {
            const tr = document.createElement('tr');
            tr.innerHTML = `<td colspan="6" style="text-align:center;">No items.</td>`;
            body.appendChild(tr);
            return;
        }

        items.forEach(item => {
            const tr = document.createElement('tr');

            tr.innerHTML = `
                <td>${item.iconPath ? `<img src="http://localhost:8080/inventory/${item.iconPath}" class="icon"/>` : ''}</td>
                <td>${item.name}</td>
                <td>${item.typeId}</td>
                <td>${item.rarityId}</td>
                <td class="${item.price <= gold ? 'price-green' : 'price-red'}">${item.price}</td>
                <td>
                    <button ${item.price > gold ? 'disabled' : ''} data-id="${item.id}">Buy</button>
                </td>
            `;

            const btn = tr.querySelector('button');
            btn.addEventListener('click', () => buyItem(item));

            body.appendChild(tr);
        });
    } catch (err) {
        showError('Failed to load shop data');
        console.error(err)
    }
}

let failures = 0;
const MAX_FAIL = 3;

async function buyItem(item) {
    clearMessages();

    try {
        const payload = { baseItemId: item.id }
        const idemKey = await getOrCreateIdempotencyKey(payload);

        const buyRes = await fetchWithResilience('http://localhost:8080/api/shop/test-buy', {
            method: 'POST',
            idempotencyKey: idemKey,
            body: JSON.stringify(payload),
            retry: { retries: 3, baseDelayMs: 300, timeoutMs: 3500, jitter: true }
        });

        const data = await buyRes.json();

        banner.style.display = "none";
        itemTable.style.display = "table"
        failures = 0;

        showMessage("Item successfully bought.\n(" + JSON.stringify(data, null, 2) + ")");

        gold -= item.price
        goldCounterView.textContent = gold
    } catch (e) {
        failures++;

        if (failures >= MAX_FAIL) {
            banner.style.display = "block";
            itemTable.style.display = "none"
        }

        showError('Buy request failed (' + e + ")");
        console.error(e)
    }
}

function showMessage(msg) {
    const el = document.getElementById('message');
    el.textContent = msg;
    el.style.display = 'block';
}

function showError(msg) {
    const el = document.getElementById('error');
    el.textContent = msg;
    el.style.display = 'block';
}

function clearMessages() {
    document.getElementById('message').style.display = 'none';
    document.getElementById('error').style.display = 'none';
}

loadData();