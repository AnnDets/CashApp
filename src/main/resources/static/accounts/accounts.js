document.addEventListener('DOMContentLoaded', () => {
    const API_BASE = 'http://localhost:8080/api/v1/accounts';
    const API_CURRENCIES = 'http://localhost:8080/api/v1/configuration/currencies';
    const API_BANKS = 'http://localhost:8080/api/v1/configuration/banks';

    const urlParams = new URLSearchParams(window.location.search);
    let userId = urlParams.get('userId') || localStorage.getItem('user_id') || '';
    const accountsList = document.getElementById('accountsList');
    const loadingEl = document.getElementById('loading');
    const alertContainer = document.getElementById('alertContainer');
    const emptyState = document.getElementById('emptyState');
    const refreshBtn = document.getElementById('refreshBtn');
    const logoutBtn = document.getElementById('logoutBtn');
    const showArchived = document.getElementById('showArchived');
    const typeFilter = document.getElementById('typeFilter');
    const createAccountBtn = document.getElementById('createAccountBtn');

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
        const credit = acc.creditLimit ? Number(acc.creditLimit).toLocaleString('ru-RU', {minimumFractionDigits: 2}) : '—';
        const bankIcon = acc.bankIcon || '';
        return `
      <div class="col-12 col-md-6 col-lg-4">
        <div class="card account-card h-100 shadow-sm">
          <div class="card-body d-flex gap-3">
            <div class="d-flex flex-column align-items-center justify-content-center">
              <div class="account-icon mb-2">${bankIcon ? `<img src="${bankIcon}" alt="" style="max-width:100%;max-height:100%;">` : acc.type.charAt(0)}</div>
              <small class="text-muted">${acc.type}</small>
            </div>

            <div class="flex-grow-1">
              <div class="d-flex justify-content-between">
                <div>
                  <div class="account-name">${escapeHtml(acc.name)}</div>
                  <div class="text-muted small">${escapeHtml(acc.currency?.displayName || '')}</div>
                </div>
                <div class="text-end">
                  <div class="account-balance">${symbol} ${balance}</div>
                  <div class="text-muted small">Кредит: ${credit}</div>
                </div>
              </div>

              <div class="mt-3 d-flex gap-2">
                <button class="btn btn-outline-secondary btn-sm btn-transactions" data-id="${acc.id}">Транзакции</button>
                <button class="btn btn-outline-primary btn-sm btn-edit" data-id="${acc.id}">Открыть</button>
                <button class="btn btn-outline-success btn-sm btn-update" data-id="${acc.id}">Редактировать</button>
              </div>
            </div>
          </div>

          <div class="card-footer text-muted small">
            ${acc.savingsAccount ? 'Сберегательный' : ''} ${acc.archiveAccount ? ' • Архивный' : ''}
          </div>
        </div>
      </div>
    `;
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

    async function loadAccounts() {
        clearAlert();
        accountsList.innerHTML = '';
        emptyState.classList.add('d-none');

        if (!userId) {
            showAlert('Не указан userId. Войдите или передайте ?userId=... в URL.', 'warning');
            return;
        }

        setLoading(true);
        try {
            const res = await fetch(`${API_BASE}?userId=${encodeURIComponent(userId)}`, {headers: authHeaders()});
            if (!res.ok) {
                if (res.status === 401) showAlert('Неавторизован. Пожалуйста, войдите снова.', 'warning');
                else {
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

            accountsList.innerHTML = filtered.map(buildAccountCard).join('');

            document.querySelectorAll('.btn-transactions').forEach(btn => {
                btn.addEventListener('click', (e) => {
                    const id = e.currentTarget.dataset.id;
                    window.location.href = `transactions.html?accountId=${encodeURIComponent(id)}`;
                });
            });
            document.querySelectorAll('.btn-edit').forEach(btn => {
                btn.addEventListener('click', (e) => {
                    const id = e.currentTarget.dataset.id;
                    window.location.href = `account.html?id=${encodeURIComponent(id)}`;
                });
            });
            document.querySelectorAll('.btn-update').forEach(btn => {
                btn.addEventListener('click', async (e) => {
                    const id = e.currentTarget.dataset.id;
                    await openEditModal(id);
                });
            });

        } catch (err) {
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
            const res = await fetch(`http://localhost:8080/api/v1/accounts/${encodeURIComponent(accountId)}`, {headers: authHeaders()});
            if (!res.ok) {
                showAlert('Не удалось загрузить данные счёта.', 'danger');
                return;
            }
            const acc = await res.json();
            fields.name.value = acc.name || '';
            fields.type.value = acc.type || 'CASH';
            fields.currencyId.value = acc.currency?.id || '';
            fields.bankId.value = acc.bank?.id || '';
            fields.creditLimit.value = acc.creditLimit || '';
            fields.currentBalance.value = acc.currentBalance || '';
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
            creditLimit: fields.creditLimit.value || '',
            currentBalance: fields.currentBalance.value || '',
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
                res = await fetch(`http://localhost:8080/api/v1/accounts/${encodeURIComponent(fields.id)}?userId=${encodeURIComponent(userId)}`, {
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
                const msg = data.message || JSON.stringify(data) || `Ошибка ${res.status}`;
                accountFormAlert.innerHTML = `<div class="alert alert-danger">${msg}</div>`;
            }
        } catch (err) {
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
        window.location.href = 'index.html';
    });

    (async function init() {
        if (!userId) {
            userId = localStorage.getItem('user_id') || '';
        }
        await loadDictionaries();
        loadAccounts();
    })();
});
