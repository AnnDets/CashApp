document.addEventListener('DOMContentLoaded', () => {
    const API_BASE = 'http://localhost:8080/api/v1/auth';

    function showAlert(containerId, message, type = 'danger') {
        const container = document.getElementById(containerId);
        container.innerHTML = `<div class="alert alert-${type} alert-sm" role="alert">${message}</div>`;
    }

    function clearAlert(containerId) {
        document.getElementById(containerId).innerHTML = '';
    }

    function setLoading(button, spinner, isLoading) {
        button.disabled = isLoading;
        spinner.classList.toggle('d-none', !isLoading);
    }

    function saveAuth(token, userId) {
        if (token) localStorage.setItem('auth_token', token);
        if (userId) localStorage.setItem('user_id', userId);
    }

    // Registration
    document.getElementById('registerForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const form = e.target;
        if (!form.checkValidity()) {
            form.classList.add('was-validated');
            return;
        }

        clearAlert('registerAlert');
        const btn = document.getElementById('registerBtn');
        const spinner = document.getElementById('registerSpinner');
        setLoading(btn, spinner, true);

        const payload = {
            username: document.getElementById('regUsername').value.trim(),
            email: document.getElementById('regEmail').value.trim(),
            password: document.getElementById('regPassword').value
        };

        try {
            const res = await fetch(`${API_BASE}/register`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(payload)
            });

            const data = await res.json().catch(() => ({}));

            if (res.ok) {
                showAlert('registerAlert', 'Регистрация успешна. Теперь можно войти.', 'success');
                const loginTab = new bootstrap.Tab(document.querySelector('#login-tab'));
                loginTab.show();
            } else {
                const msg = data.message || JSON.stringify(data) || 'Ошибка регистрации';
                showAlert('registerAlert', msg, 'danger');
            }
        } catch (err) {
            showAlert('registerAlert', 'Сетевая ошибка. Проверьте API и CORS.', 'danger');
        } finally {
            setLoading(btn, spinner, false);
        }
    });

    // Login
    document.getElementById('loginForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const form = e.target;
        if (!form.checkValidity()) {
            form.classList.add('was-validated');
            return;
        }

        clearAlert('loginAlert');
        const btn = document.getElementById('loginBtn');
        const spinner = document.getElementById('loginSpinner');
        setLoading(btn, spinner, true);

        const payload = {
            email: document.getElementById('loginEmail').value.trim(),
            password: document.getElementById('loginPassword').value
        };

        try {
            const res = await fetch(`${API_BASE}/login`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(payload)
            });

            const data = await res.json().catch(() => ({}));

            if (res.ok) {
                const userId = data.id;
                const token = data.token || data.accessToken || data.access_token || null;
                if (!userId) {
                    showAlert('loginAlert', 'В ответе сервера не найден user id.', 'warning');
                } else {
                    saveAuth(token, userId);
                    window.location.href = `accounts.html?userId=${encodeURIComponent(userId)}`;
                }
            } else {
                const msg = data.message || JSON.stringify(data) || 'Ошибка входа';
                showAlert('loginAlert', msg, 'danger');
            }
        } catch (err) {
            showAlert('loginAlert', 'Сетевая ошибка. Проверьте API и CORS.', 'danger');
        } finally {
            setLoading(btn, spinner, false);
        }
    });
});
