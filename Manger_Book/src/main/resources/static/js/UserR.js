document.getElementById('registerForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 阻止默认提交行为

    const username = document.getElementById('registerUsername').value;
    const password = document.getElementById('registerPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const errorMessageElem = document.getElementById('error-message');

    // 清除之前的错误信息
    errorMessageElem.style.display = "none";

    // 验证密码是否匹配
    if (password !== confirmPassword) {
        errorMessageElem.innerText = "密码不匹配";
        errorMessageElem.style.display = "block"; // 显示错误信息
        return;
    }

    // 验证密码是否包含字母和数字
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/; // 至少8个字符，包含字母和数字
    if (!passwordRegex.test(password)) {
        errorMessageElem.innerText = "密码必须至少8个字符，并且包含字母和数字";
        errorMessageElem.style.display = "block"; // 显示错误信息
        return;
    }

    // 发送注册请求
    fetch('/api/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password, role: 'regular' }), // 可以根据需要添加角色
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => { throw new Error(errorData.message || "注册失败"); });
            }
            return response.json();
        })
        .then(data => {
            alert('注册成功！'); // 注册成功的提示
            window.location.href = '/login.html'; // 可以选择重定向到登录页面
        })
        .catch(error => {
            errorMessageElem.innerText = error.message; // 显示错误信息
            errorMessageElem.style.display = "block"; // 显示错误信息
        });
});
