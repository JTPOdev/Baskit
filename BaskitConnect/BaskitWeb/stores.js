import { BASE_URL } from "./config.js"; 

document.addEventListener("DOMContentLoaded", () => {
    const navLinks = document.querySelectorAll("ul li a");
    const currentPage = window.location.pathname.split("/").pop(); 

    navLinks.forEach(link => {
        if (link.getAttribute("href") === currentPage) {
            link.classList.add("active");
        } else {
            link.classList.remove("active");
        }
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const heading = document.querySelector(".header-left h1");

    heading.classList.add("active"); 

    heading.addEventListener("click", function () {
        this.classList.add("active"); 
    });
});


document.addEventListener("DOMContentLoaded", function () {
    const nav = document.querySelector("nav");

    setTimeout(() => {
        nav.classList.add("show");
    }, 100);
});

document.addEventListener("DOMContentLoaded", function () {
    fetchStores();
});

let allStores = [];
let isAllSelected = true;

function fetchStores() {
    fetch(`${BASE_URL}/store/all`)
        .then(response => response.json())
        .then(data => {
            console.log("Fetched stores:", data);
            allStores = data;
            filterStores();
        })
        .catch(error => console.error("Error fetching stores:", error));
}

function displayStores(stores) {
    const storeContainer = document.getElementById("storeContainer");
    storeContainer.innerHTML = "";

    if (stores.length === 0) {
        storeContainer.innerHTML = "<p>No stores found.</p>";
        return;
    }

    stores.forEach(store => {
        const storeDiv = document.createElement("div");
        storeDiv.classList.add("store-item");

        const storeImg = document.createElement("img");
        storeImg.src = store.store_image || "img/default.png";
        storeImg.alt = store.store_name;

        const storeName = document.createElement("div");
        storeName.classList.add("store-name");
        storeName.textContent = store.store_name;

        const storeDetails = document.createElement("div");
        storeDetails.classList.add("store-details");

        const storeOwner = document.createElement("div");
        storeOwner.innerHTML = "<span>Owner:</span> " + store.owner_name;

        const storeContact = document.createElement("div");
        storeContact.innerHTML = "<span>Contact:</span> " + store.store_phone_number;

        const storeType = document.createElement("div");
        storeType.innerHTML = "<span>Store Type:</span> " + store.store_status;

        const storeOrigin = document.createElement("div");
        storeOrigin.innerHTML = "<span>Location:</span> " + store.store_origin;

        storeDetails.appendChild(storeOwner);
        storeDetails.appendChild(storeContact);
        storeDetails.appendChild(storeType);
        storeDetails.appendChild(storeOrigin);

        storeDiv.appendChild(storeImg);
        storeDiv.appendChild(storeName);
        storeDiv.appendChild(storeDetails);

        storeDiv.addEventListener("click", function () {
            showProducts(store.store_name);
        });

        storeContainer.appendChild(storeDiv);
    });
}

function filterStores() {
    if (isAllSelected) {
        displayStores(allStores);
        return;
    }

    const selectedType = document.getElementById("filterSelect").value;
    const selectedLocation = document.getElementById("locationSelect").value;
    const normalizedType = selectedType.toLowerCase() === "partnership" ? "partner" : selectedType.toLowerCase();

    let filteredStores = allStores.filter(store => {
        const matchesType = store.store_status.toLowerCase() === normalizedType;
        const matchesLocation = store.store_origin.toLowerCase() === selectedLocation;
        return matchesType && matchesLocation;
    });

    console.log("Filtered stores:", filteredStores);
    displayStores(filteredStores);
}

document.getElementById("allButton").addEventListener("click", function () {
    isAllSelected = !isAllSelected;
    this.classList.toggle("active", isAllSelected);

    if (isAllSelected) {
        document.getElementById("filterSelect").selectedIndex = 0;
        document.getElementById("locationSelect").selectedIndex = 0;
    }

    filterStores();
});


document.getElementById("filterSelect").addEventListener("change", function () {
    isAllSelected = false;
    document.getElementById("allButton").classList.remove("active");
    filterStores();
});

document.getElementById("locationSelect").addEventListener("change", function () {
    isAllSelected = false;
    document.getElementById("allButton").classList.remove("active");
    filterStores();
});

let storeProducts = {};

function showProducts(storeName) {
    document.getElementById("allButton").style.display = "none";
    document.getElementById("storeContainer").style.display = "none";
    document.getElementById("productContainer").style.display = "block";
    document.getElementById("filterSelect").style.display = "none";
    document.getElementById("locationSelect").style.display = "none";
    document.getElementById("categorySelect").style.display = "inline-block";
    document.getElementById("backButton").style.display = "inline-block";

    document.querySelector(".header-left h1").textContent = storeName;
    document.getElementById("categorySelect").dataset.storeName = storeName;

    document.getElementById("categorySelect").value = "all";

    const token = localStorage.getItem("access_token");
    if (!token) {
        alert("Session expired. Please log in again.");
        window.location.href = "login.html";
        return;
    }

    fetch(`${BASE_URL}/product/list`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`,
            "Accept": "application/json"
        }
    })
    .then(response => {
        if (response.status === 401) {
            alert("Session expired. Please log in again.");
            localStorage.clear();
            window.location.href = "login.html";
            return;
        }
        return response.json();
    })
    .then(data => {
        if (!data) return;
        const filteredProducts = data.filter(product => product.store_name === storeName);
        storeProducts[storeName] = filteredProducts;

        displayProducts(storeName, "all");
    })
    .catch(error => console.error("Error fetching products:", error));
}

function displayProducts(storeName, category) {
    const productList = document.getElementById("productList");
    productList.innerHTML = "";

    if (!storeProducts[storeName] || storeProducts[storeName].length === 0) {
        productList.innerHTML = "<p>No products available.</p>";
        return;
    }

    const products = storeProducts[storeName];

    const filteredProducts = category === "all"
        ? products
        : products.filter(p => p.product_category.toLowerCase() === category.toLowerCase());

    if (filteredProducts.length === 0) {
        productList.innerHTML = "<p>No products available in this category.</p>";
    } else {
        filteredProducts.forEach(product => {
            const productCard = document.createElement("div");
            productCard.classList.add("product-card");

            productCard.innerHTML = `
                <img src="${product.product_image}" alt="${product.product_name}">
                <h3>${product.product_name} - ₱${product.product_price}</h3>
            `;

            productList.appendChild(productCard);
        });
    }
}

document.getElementById("categorySelect").addEventListener("change", function () {
    const storeName = this.dataset.storeName;
    const selectedCategory = this.value;
    displayProducts(storeName, selectedCategory);
});

document.getElementById("backButton").addEventListener("click", function () {
    document.getElementById("productContainer").style.display = "none";
    document.getElementById("storeContainer").style.display = "flex";
    document.getElementById("filterSelect").style.display = "inline-block";
    document.getElementById("locationSelect").style.display = "inline-block";
    document.getElementById("categorySelect").style.display = "none";
    document.getElementById("backButton").style.display = "none";
    document.querySelector(".header-left h1").textContent = "Stores";

    const allButton = document.getElementById("allButton");
    allButton.style.display = "inline-block";
    allButton.classList.add("active");
});

