document.addEventListener('DOMContentLoaded', function() {
    const requestsList = document.getElementById('requestsList');

    document.getElementById('logoutLink').addEventListener('click', function(event) {
        event.preventDefault(); // 防止默认跳转行为
        localStorage.removeItem('userToken'); // 清除登录信息
        window.location.href = '../login.html'; // 跳转到登录页面
    });

    function loadRequests() {
        fetch('/api/requests/pending')
            .then(response => {
                if (!response.ok) throw new Error('加载借阅请求失败');
                return response.json();
            })
            .then(requests => {
                requestsList.innerHTML = ''; // 清空列表
                requests.forEach(request => {
                    const li = document.createElement('li');
                    li.innerHTML = `
                        ${request.username} - 借阅《${request.bookTitle}》 
                        <button class="approve-btn" data-request-id="${request.id}">批准</button>
                        <button class="reject-btn" data-request-id="${request.id}">拒绝</button>
                    `;
                    requestsList.appendChild(li);
                });
            })
            .catch(error => {
                console.error('加载借阅请求失败:', error);
            });
    }

    requestsList.addEventListener('click', function(event) {
        const requestId = event.target.getAttribute('data-request-id');
        if (event.target.classList.contains('approve-btn') || event.target.classList.contains('reject-btn')) {
            const action = event.target.classList.contains('approve-btn') ? 'approve' : 'reject';
            const button = event.target;

            button.disabled = true; // 禁用按钮以防重复点击

            fetch(`/api/requests/${requestId}/${action}`, { method: 'POST' })
                .then(response => {
                    if (!response.ok) {
                        button.disabled = false; // 处理失败时启用按钮
                        return response.text().then(text => { throw new Error(text); });
                    }
                    loadRequests(); // 重新加载请求列表
                    alert(`请求已${action === 'approve' ? '批准' : '拒绝'}！`);
                })
                .catch(error => {
                    button.disabled = false; // 处理失败时启用按钮
                    alert('处理请求失败: ' + error.message);
                });
        }
    });
    function loadBorrowRecords() {
        fetch('/api/borrow')
            .then(response => {
                if (!response.ok) throw new Error('加载借阅记录失败');
                return response.json();
            })
            .then(records => {
                const borrowRecordsList = document.getElementById('borrowRecordsList');
                borrowRecordsList.innerHTML = '';
                records.forEach(record => {
                    const li = document.createElement('li');
                    li.textContent = `用户 ID: ${record.userId} 借阅图书 ID: ${record.bookId} 借阅日期: ${record.borrowDate}`;
                    borrowRecordsList.appendChild(li);
                });
            })
            .catch(error => {
                console.error('加载借阅记录失败:', error);
            });
    }

    document.addEventListener('DOMContentLoaded', function() {
        loadRequests();
        loadBorrowRecords(); // 加载借阅记录
    });



    loadRequests(); // 初始加载借阅请求
});
