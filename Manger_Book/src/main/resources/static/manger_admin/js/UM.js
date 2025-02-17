document.addEventListener('DOMContentLoaded', () => {
    const userForm = document.getElementById('userForm');
    const userTableBody = document.querySelector('#userTable tbody');

    document.getElementById('logoutLink').addEventListener('click', function(event) {
        event.preventDefault();
        localStorage.removeItem('userToken');
        window.location.href = '../login.html';
    });

    userForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const role = document.getElementById('role').value;

        fetch('/api/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password, role })
        })
            .then(response => {
                if (response.ok) {
                    alert('用户添加成功！');
                    loadUsers();
                    userForm.reset();
                } else {
                    return response.text().then(text => { throw new Error(text); });
                }
            })
            .catch(error => {
                alert('添加用户失败: ' + error.message);
            });
    });

    function loadUsers() {
        fetch('/api/users')
            .then(response => response.json())
            .then(users => {
                userTableBody.innerHTML = '';
                users.forEach(user => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.role}</td>
                        <td>
                            <button class="delete-btn" data-user-id="${user.id}">删除</button>
                        </td>
                    `;
                    userTableBody.appendChild(row);
                });
            })
            .catch(error => {
                console.error('加载用户失败:', error);
            });
    }

    userTableBody.addEventListener('click', function(event) {
        if (event.target.classList.contains('delete-btn')) {
            const userId = event.target.getAttribute('data-user-id');
            fetch(`/api/users/${userId}`, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        alert('用户已删除！');
                        loadUsers();
                    } else {
                        return response.text().then(text => { throw new Error(text); });
                    }
                })
                .catch(error => {
                    alert('删除用户失败: ' + error.message);
                });
        }
    });

    loadUsers();
});
