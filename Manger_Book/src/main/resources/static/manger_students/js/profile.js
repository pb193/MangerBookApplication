async function fetchUserProfile() {
    try {
        const userId = getUserId(); // 动态获取用户 ID

        const response = await fetch(`/api/users/${userId}`);
        if (!response.ok) throw new Error('无法获取用户信息');

        const user = await response.json();
        document.getElementById('username').textContent = `用户名: ${user.username}`;
        document.getElementById('registrationDate').textContent = `注册日期: ${formatDate(user.createdAt)}`;
    } catch (error) {
        alert(`错误: ${error.message}`);
    }
}

function getUserId() {
    // 从 localStorage、session 或者其他方式获取用户 ID
    return 1; // 示例
}

function formatDate(dateString) {
    const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' };
    return new Date(dateString).toLocaleDateString(undefined, options);
}

fetchUserProfile();
