const passwordField = document.getElementById('password');
const togglePassword = document.getElementById('togglePassword');
const loginForm = document.getElementById('loginForm');
const errorMessage = document.getElementById('errorMessage');

 const adminEmail = "admin123";
const adminPassword = "admin123";

 togglePassword.addEventListener('click', () => {
    const isPasswordVisible = passwordField.type === 'password';
    passwordField.type = isPasswordVisible ? 'text' : 'password';
    togglePassword.textContent = isPasswordVisible ? 'HIDE' : 'SHOW';
});

 loginForm.addEventListener('submit', function(event) {
    event.preventDefault();
    const enteredEmail = document.getElementById('email').value;
    const enteredPassword = passwordField.value;

    if (enteredEmail === adminEmail && enteredPassword === adminPassword) {
        localStorage.setItem("isLoggedIn", "true");  
        alert("Login successful!");
        window.location.href = "summary.html";  
    } else {
        errorMessage.style.display = "block";
    }
});


document.getElementById("loginForm").addEventListener("submit", function(event) {
event.preventDefault();

const enteredEmail = document.getElementById("email").value;
const enteredPassword = document.getElementById("password").value;

const storedEmail = localStorage.getItem("adminEmail") || "admin123";
const storedPassword = localStorage.getItem("adminPassword") || "admin123";

if (enteredEmail === storedEmail && enteredPassword === storedPassword) {
localStorage.setItem("isLoggedIn", "true");
localStorage.setItem("username", enteredEmail);
alert("Login successful!");
window.location.href = "dashboard.html";
} else {
document.getElementById("errorMessage").style.display = "block";
}
});
