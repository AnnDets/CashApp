// accounts.js (без ?userId — берём user_id из localStorage)
console.log('accounts.js loaded');

document.addEventListener('DOMContentLoaded', () => {
    const API_BASE = '/api/v1/accounts';
    const API_CURRENCIES = '/api/v1/configuration/currencies';
    const API_BANKS = '/api/v1/configuration/banks';

    // userId только из localStorage
    let userId = localStorage.getItem('user_id') || null;

    if (!userId) {
        console.warn('user_id not found in localStorage — redirect to auth');
        window.location.href = '/auth';
        return;
    }

    // Элементы
    const accountsList = document.getElementById('accountsList');
    const loadingEl = document.getElementById('loading');
    const alertContainer = document.getElementById('alertContainer');
    const emptyState = document.getElementById('emptyState');
    const refreshBtn = document.getElementById('refreshBtn');
    const logoutBtn = document.getElementById('logoutBtn');
    const showArchived = document.getElementById('showArchived');
    const typeFilter = document.getElementById('typeFilter');
    const createAccountBtn = document.getElementById('createAccountBtn');

    // Modal
    const accountModalEl = document.getElementById('accountModal');
    const accountModal = new bootstrap.Modal(accountModalEl);
    const accountForm = document.getElementById('accountForm');
    const accountModalTitle = document.getElementById('accountModalTitle');
    const accountFormAlert = document.getElementById('accountFormAlert');
    const accountSaveBtn = document.getElementById('accountSaveBtn');

    const fields = {
        id: null,
        name: document.getElementById('acc_name'),
        type: document.getElementById('acc_type'),
        currencyId: document.getElementById('acc_currencyId'),
        bankId: document.getElementById('acc_bankId'),
        creditLimit: document.getElementById('acc_creditLimit'),
        currentBalance: document.getElementById('acc_currentBalance'),
        includeInTotalBalance: document.getElementById('acc_includeInTotalBalance'),
        defaultAccount: document.getElementById('acc_defaultAccount'),
        cardNumber1: document.getElementById('acc_cardNumber1'),
        cardNumber2: document.getElementById('acc_cardNumber2'),
        cardNumber3: document.getElementById('acc_cardNumber3'),
        cardNumber4: document.getElementById('acc_cardNumber4'),
        savingsAccount: document.getElementById('acc_savingsAccount'),
        archiveAccount: document.getElementById('acc_archiveAccount')
    };

    function showAlert(message, type = 'danger') {
        alertContainer.innerHTML = `<div class="alert alert-${type}" role="alert">${message}</div>`;
    }

    function clearAlert() {
        alertContainer.innerHTML = '';
    }
    function setLoading(isLoading) {
        loadingEl.classList.toggle('d-none', !isLoading);
        refreshBtn.disabled = isLoading;
    }

    function getToken() {
        return localStorage.getItem('auth_token');
    }

    function authHeaders() {
        const headers = {'Accept': 'application/json', 'Content-Type': 'application/json'};
        const token = getToken();
        if (token) headers['Authorization'] = 'Bearer ' + token;
        return headers;
    }

    function escapeHtml(str) {
        if (!str && str !== 0) return '';
        return String(str)
            .replaceAll('&', '&amp;')
            .replaceAll('<', '&lt;')
            .replaceAll('>', '&gt;')
            .replaceAll('"', '&quot;')
            .replaceAll("'", '&#039;');
    }

    async function loadDictionaries() {
        try {
            const res = await fetch(API_CURRENCIES, {headers: {'Accept': 'application/json'}});
            if (res.ok) {
                const list = await res.json();
                fields.currencyId.innerHTML = '<option value="">Выберите валюту</option>' + list.map(c => `<option value="${c.id}">${escapeHtml(c.displayName)} ${escapeHtml(c.symbol || '')}</option>`).join('');
            } else {
                fields.currencyId.innerHTML = '<option value="">Ошибка загрузки</option>';
            }
        } catch (e) {
            fields.currencyId.innerHTML = '<option value="">Ошибка сети</option>';
        }

        try {
            const res = await fetch(API_BANKS, {headers: {'Accept': 'application/json'}});
            if (res.ok) {
                const list = await res.json();
                fields.bankId.innerHTML = '<option value="">Не выбран</option>' + list.map(b => `<option value="${b.id}">${escapeHtml(b.displayName)}</option>`).join('');
            } else {
                fields.bankId.innerHTML = '<option value="">Ошибка загрузки</option>';
            }
        } catch (e) {
            fields.bankId.innerHTML = '<option value="">Ошибка сети</option>';
        }
    }

    function buildAccountCard(acc) {
        const symbol = (acc.currency && acc.currency.symbol) ? acc.currency.symbol : '';
        const balance = Number(acc.currentBalance || 0).toLocaleString('ru-RU', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        });
        const credit = (acc.creditLimit !== undefined && acc.creditLimit !== null) ? Number(acc.creditLimit).toLocaleString('ru-RU', {minimumFractionDigits: 2}) : '—';
        const bankIcon = acc.bankIcon || '';
        return `
      <div class="card account-card">
        <div class="card-body">
          <div class="account-icon">${bankIcon ? `<img src="${bankIcon}" alt="">` : escapeHtml(acc.type?.charAt(0) || '')}</div>
          <div class="flex-grow-1">
            <div class="account-name">${escapeHtml(acc.name)}</div>
            <div class="account-meta">${escapeHtml(acc.currency?.displayName || '')}</div>
          </div>
          <div class="text-end">
            <div class="account-balance">${symbol} ${balance}</div>
            <div class="text-muted small">Кредит: ${credit}</div>
          </div>
        </div>
        <div class="card-footer d-flex gap-2">
          <div class="btn-group">
            <button class="btn btn-outline-secondary btn-sm btn-transactions" data-id="${acc.id}">Транзакции</button>
            <button class="btn btn-outline-primary btn-sm btn-edit" data-id="${acc.id}">Открыть</button>
            <button class="btn btn-outline-success btn-sm btn-update" data-id="${acc.id}">Редактировать</button>
          </div>
          <div class="ms-auto text-muted small align-self-center">
            ${acc.savingsAccount ? 'Сберегательный' : ''} ${acc.archiveAccount ? ' • Архивный' : ''}
          </div>
        </div>
      </div>
    `;
    }

    async function loadAccounts() {
        clearAlert();
        accountsList.innerHTML = '';
        emptyState.classList.add('d-none');

        setLoading(true);
        try {
            const res = await fetch(`${API_BASE}?userId=${encodeURIComponent(userId)}`, {headers: authHeaders()});
            if (!res.ok) {
                if (res.status === 401) {
                    showAlert('Неавторизован. Пожалуйста, войдите снова.', 'warning');
                    localStorage.removeItem('auth_token');
                    localStorage.removeItem('user_id');
                    setTimeout(() => window.location.href = '/auth', 900);
                } else {
                    const err = await res.text().catch(() => '');
                    showAlert(`Ошибка сервера: ${res.status} ${err}`, 'danger');
                }
                return;
            }

            const data = await res.json().catch(() => []);
            if (!Array.isArray(data) || data.length === 0) {
                emptyState.classList.remove('d-none');
                return;
            }

            const filtered = data.filter(acc => {
                if (!showArchived.checked && acc.archiveAccount) return false;
                const type = typeFilter.value;
                if (type && acc.type !== type) return false;
                return true;
            });

            if (filtered.length === 0) {
                emptyState.classList.remove('d-none');
                return;
            }

            // Рендерим сетку: вставляем карточки в .accounts-grid
            const html = filtered.map(buildAccountCard).join('');
            accountsList.innerHTML = html;

            // Навешиваем обработчики
            document.querySelectorAll('.btn-transactions').forEach(btn => {
                btn.addEventListener('click', (e) => {
                    const id = e.currentTarget.dataset.id;
                    window.location.href = `/transactions?accountId=${encodeURIComponent(id)}`;
                });
            });
            document.querySelectorAll('.btn-edit').forEach(btn => {
                btn.addEventListener('click', (e) => {
                    const id = e.currentTarget.dataset.id;
                    window.location.href = `/account?id=${encodeURIComponent(id)}`;
                });
            });
            document.querySelectorAll('.btn-update').forEach(btn => {
                btn.addEventListener('click', async (e) => {
                    const id = e.currentTarget.dataset.id;
                    await openEditModal(id);
                });
            });

        } catch (err) {
            console.error('loadAccounts error', err);
            showAlert('Сетевая ошибка. Проверьте API и CORS.', 'danger');
        } finally {
            setLoading(false);
        }
    }

    createAccountBtn.addEventListener('click', async () => {
        fields.id = null;
        accountModalTitle.textContent = 'Создать счёт';
        accountForm.reset();
        accountForm.classList.remove('was-validated');
        accountFormAlert.innerHTML = '';
        await loadDictionaries();
        accountModal.show();
    });

    async function openEditModal(accountId) {
        accountFormAlert.innerHTML = '';
        accountModalTitle.textContent = 'Редактировать счёт';
        accountForm.classList.remove('was-validated');
        fields.id = accountId;
        await loadDictionaries();

        try {
            const res = await fetch(`/api/v1/accounts/${encodeURIComponent(accountId)}`, {headers: authHeaders()});
            if (!res.ok) {
                showAlert('Не удалось загрузить данные счёта.', 'danger');
                return;
            }
            const acc = await res.json();
            fields.name.value = acc.name || '';
            fields.type.value = acc.type || 'CASH';
            fields.currencyId.value = acc.currency?.id || '';
            fields.bankId.value = acc.bank?.id || null;
            fields.creditLimit.value = acc.creditLimit || null;
            fields.currentBalance.value = acc.currentBalance || null;
            fields.includeInTotalBalance.checked = !!acc.includeInTotalBalance;
            fields.defaultAccount.checked = !!acc.defaultAccount;
            fields.cardNumber1.value = acc.cardNumber1 || '';
            fields.cardNumber2.value = acc.cardNumber2 || '';
            fields.cardNumber3.value = acc.cardNumber3 || '';
            fields.cardNumber4.value = acc.cardNumber4 || '';
            fields.savingsAccount.checked = !!acc.savingsAccount;
            fields.archiveAccount.checked = !!acc.archiveAccount;

            accountModal.show();
        } catch (e) {
            console.error('openEditModal error', e);
            showAlert('Сетевая ошибка при загрузке счёта.', 'danger');
        }
    }

    accountForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        if (!accountForm.checkValidity()) {
            accountForm.classList.add('was-validated');
            return;
        }
        accountFormAlert.innerHTML = '';
        accountSaveBtn.disabled = true;

        const payload = {
            name: fields.name.value.trim(),
            type: fields.type.value,
            currencyId: fields.currencyId.value || null,
            creditLimit: fields.creditLimit.value || null,
            currentBalance: fields.currentBalance.value || null,
            includeInTotalBalance: fields.includeInTotalBalance.checked,
            defaultAccount: fields.defaultAccount.checked,
            bankId: fields.bankId.value || null,
            cardNumber1: fields.cardNumber1.value || '',
            cardNumber2: fields.cardNumber2.value || '',
            cardNumber3: fields.cardNumber3.value || '',
            cardNumber4: fields.cardNumber4.value || '',
            savingsAccount: fields.savingsAccount.checked,
            archiveAccount: fields.archiveAccount.checked
        };

        try {
            let res;
            if (fields.id) {
                res = await fetch(`/api/v1/accounts/${encodeURIComponent(fields.id)}?userId=${encodeURIComponent(userId)}`, {
                    method: 'PATCH',
                    headers: authHeaders(),
                    body: JSON.stringify(payload)
                });
            } else {
                res = await fetch(`${API_BASE}?userId=${encodeURIComponent(userId)}`, {
                    method: 'POST',
                    headers: authHeaders(),
                    body: JSON.stringify(payload)
                });
            }

            const data = await res.json().catch(() => ({}));
            if (res.ok) {
                accountModal.hide();
                loadAccounts();
            } else {
                const msg = data?.message || JSON.stringify(data) || `Ошибка ${res.status}`;
                accountFormAlert.innerHTML = `<div class="alert alert-danger">${msg}</div>`;
            }
        } catch (err) {
            console.error('account save error', err);
            accountFormAlert.innerHTML = `<div class="alert alert-danger">Сетевая ошибка. Проверьте API и CORS.</div>`;
        } finally {
            accountSaveBtn.disabled = false;
        }
    });

    refreshBtn.addEventListener('click', loadAccounts);
    showArchived.addEventListener('change', loadAccounts);
    typeFilter.addEventListener('change', loadAccounts);

    logoutBtn.addEventListener('click', () => {
        localStorage.removeItem('auth_token');
        localStorage.removeItem('user_id');
        window.location.href = '/auth';
    });

    (async function init() {
        await loadDictionaries();
        loadAccounts();
    })();
});
