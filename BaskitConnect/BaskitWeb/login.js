import { BASE_URL } from "./config.js"; 

const passwordField = document.getElementById('password');
const togglePassword = document.getElementById('togglePassword');
const loginForm = document.getElementById('loginForm');
const errorMessage = document.getElementById('errorMessage');

togglePassword.addEventListener('click', () => {
    const isPasswordVisible = passwordField.type === 'password';
    passwordField.type = isPasswordVisible ? 'text' : 'password';
    togglePassword.textContent = isPasswordVisible ? 'HIDE' : 'SHOW';
});

loginForm.addEventListener('submit', async function(event) {
    event.preventDefault();
    const username = document.getElementById('email').value;
    const password = passwordField.value;

    try {
        const response = await fetch(`${BASE_URL}/admin/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        const responseText = await response.text();
        console.log('Raw response:', responseText);

        let data;
        try {
            data = JSON.parse(responseText);
        } catch (e) {
            console.error('Failed to parse JSON:', e);  
            throw new Error('Server returned invalid JSON');
        }

        if (response.ok) {
            if (data.access_token) {
                localStorage.setItem('access_token', data.access_token);
                localStorage.setItem('admin_username', data.username);
                localStorage.setItem('isLoggedIn', 'true');
                window.location.href = 'dashboard.html';
            } else {
                console.error("Login response missing token:", data);
                errorMessage.style.display = 'block';
                errorMessage.textContent = 'Login failed: Missing access token.';
            }
        } else {
            errorMessage.style.display = 'block';
            errorMessage.textContent = data.message || 'Invalid credentials';
        } 
    } catch (error) {
        console.error('Error:', error);
        errorMessage.style.display = 'block';
        errorMessage.textContent = 'An error occurred. Please try again.';
    }
});
