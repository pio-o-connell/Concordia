async function fetchHistory() {
    const response = await fetch(
        "/Concordia/api/concordia?type=transactionHistory"
    );
    return response.json();
}

function renderHistoryTable(data) {
    const tbody = document.getElementById("history-tbody");
    tbody.innerHTML = "";
    data.forEach((tx) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${tx.deliveryDate || ""}</td>
            <td>${tx.serviceName || ""}</td>
            <td>${tx.location || ""}</td>
            <td>${tx.serviceSnapshotSize || ""}</td>
            <td>${tx.quantity || ""}</td>
            <td>${tx.provider || ""}</td>
            <td>${tx.notes || ""}</td>
        `;
        tbody.appendChild(tr);
    });
}

function renderInitialHistory(data) {
    // Show the latest 6 transactions (assume data is sorted newest first)
    renderHistoryTable(data.slice(0, 6));
    // Enable scrolling for up to 100 transactions
    const tbody = document.getElementById("history-tbody");
    tbody.style.maxHeight = "320px";
    tbody.style.overflowY = "auto";
    // Store all data for later scrolling/filtering
    window.fullHistoryData = data.slice(0, 100);
}
}

function applyFilters(data) {
    const date = document.getElementById("filter-date").value;
    const service = document
        .getElementById("filter-service")
        .value.toLowerCase();
    const location = document
        .getElementById("filter-location")
        .value.toLowerCase();
    return data.filter((tx) => {
        return (
            (!date || tx.deliveryDate === date) &&
            (!service ||
                (tx.serviceName || "").toLowerCase().includes(service)) &&
            (!location || (tx.location || "").toLowerCase().includes(location))
        );
    });
}

document.getElementById("apply-filters").addEventListener("click", async () => {
    const data = window.fullHistoryData || (await fetchHistory());
    const filtered = applyFilters(data);
    renderHistoryTable(filtered);
});

// Initial load
fetchHistory().then(renderInitialHistory);
