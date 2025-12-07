// auth.js — сохраняет user_id и редиректит на /accounts (без ?userId)
console.log('auth.js loaded');

document.addEventListener('DOMContentLoaded', () => {
    const API_BASE = '/api/v1/auth';

    function el(id) {
        return document.getElementById(id);
    }
    function showAlert(containerId, message, type = 'danger') {
        const c = el(containerId);
        if (c) c.innerHTML = `<div class="alert alert-${type}" role="alert">${message}</div>`;
    }
    function clearAlert(containerId) {
        const c = el(containerId);
        if (c) c.innerHTML = '';
    }

    function setLoading(btn, spinner, loading) {
        if (btn) btn.disabled = loading;
        if (spinner) spinner.classList.toggle('d-none', !loading);
    }
    function saveAuth(token, userId) {
        if (token) localStorage.setItem('auth_token', token);
        if (userId) localStorage.setItem('user_id', userId);
    }

    async function safeJson(res) {
        try {
            return await res.json();
        } catch (e) {
            return null;
        }
    }

    const registerForm = el('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            if (!registerForm.checkValidity()) {
                registerForm.classList.add('was-validated');
                return;
            }
            clearAlert('registerAlert');
            const btn = el('registerBtn');
            const spinner = el('registerSpinner');
            setLoading(btn, spinner, true);

            const payload = {
                username: el('regUsername').value.trim(),
                email: el('regEmail').value.trim(),
                password: el('regPassword').value
            };

            try {
                const res = await fetch(`${API_BASE}/register`, {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(payload)
                });

                const data = await safeJson(res);
                console.log('register response', res.status, data);

                if (res.ok) {
                    const userId = data?.id || null;
                    const token = data?.token || data?.accessToken || data?.access_token || null;
                    if (!userId) {
                        showAlert('registerAlert', 'Регистрация успешна, но user id не получен.', 'warning');
                        saveAuth(token, null);
                    } else {
                        saveAuth(token, userId);
                        window.location.href = '/accounts';
                    }
                } else {
                    const msg = data?.message || data?.error || `Ошибка регистрации ${res.status}`;
                    showAlert('registerAlert', msg, 'danger');
                }
            } catch (err) {
                console.error('register error', err);
                showAlert('registerAlert', 'Сетевая ошибка. Проверьте API и CORS.', 'danger');
            } finally {
                setLoading(btn, spinner, false);
            }
        });
    }

    const loginForm = el('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            if (!loginForm.checkValidity()) {
                loginForm.classList.add('was-validated');
                return;
            }
            clearAlert('loginAlert');
            const btn = el('loginBtn');
            const spinner = el('loginSpinner');
            setLoading(btn, spinner, true);

            const payload = {
                email: el('loginEmail').value.trim(),
                password: el('loginPassword').value
            };

            try {
                const res = await fetch(`${API_BASE}/login`, {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(payload)
                });

                const data = await safeJson(res);
                console.log('login response', res.status, data);

                if (res.ok) {
                    const userId = data?.id || data?.user?.id || null;
                    const token = data?.token || data?.accessToken || data?.access_token || null;
                    if (!userId) {
                        showAlert('loginAlert', 'В ответе сервера не найден user id.', 'warning');
                    } else {
                        saveAuth(token, userId);
                        window.location.href = '/accounts';
                    }
                } else {
                    const msg = data?.message || data?.error || `Ошибка входа ${res.status}`;
                    showAlert('loginAlert', msg, 'danger');
                }
            } catch (err) {
                console.error('login error', err);
                showAlert('loginAlert', 'Сетевая ошибка. Проверьте API и CORS.', 'danger');
            } finally {
                setLoading(btn, spinner, false);
            }
        });
    }
});
