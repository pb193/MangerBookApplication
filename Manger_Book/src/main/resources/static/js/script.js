document.getElementById('loginForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch('login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    })
        .then(response => {
            if (response.ok) {
                return response.json(); // 转换为 JSON 对象
            } else if (response.status === 401) {
                throw new Error('用户名或密码错误');
            } else {
                throw new Error('登录失败: ' + response.status);
            }
        })
        .then(data => {
            const role = data.role; // 从 JSON 对象中提取角色
            if (role === "admin") {
                window.location.href ="../manger_admin/admin.html"
            } else if (role === "regular") {
                window.location.href = "../manger_students/home.html";
            } else {
                alert('未知角色');
            }
        })
        .catch(error => {
            alert(error.message);
            console.error('Error:', error);
        });
});
