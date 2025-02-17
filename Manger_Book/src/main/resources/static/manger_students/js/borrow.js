const USER_ID = 1; // 替换为实际用户 ID

async function fetchBooks() {
    try {
        const response = await fetch('/api/books');
        if (!response.ok) throw new Error('无法获取书籍列表');

        const books = await response.json();
        const bookList = document.getElementById('bookList');
        bookList.innerHTML = ''; // 清空之前的书籍列表

        books.forEach(book => {
            const listItem = document.createElement('li');
            listItem.textContent = `${book.title} - ${book.author}`;

            const borrowButton = document.createElement('button');
            borrowButton.textContent = "借阅";
            borrowButton.onclick = () => borrowBook(book.id, borrowButton); // 传入按钮以禁用它
            listItem.appendChild(borrowButton);
            bookList.appendChild(listItem);
        });
    } catch (error) {
        alert(`错误: ${error.message}`);
    }
}

async function borrowBook(bookId, borrowButton) {
    try {
        borrowButton.disabled = true; // 禁用按钮
        const response = await fetch(`/api/books/borrow/${bookId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ userId: USER_ID }) // 使用常量
        });

        if (!response.ok) {
            const errorData = await response.json(); // 处理 JSON 错误消息
            throw new Error(errorData.message || '借阅请求失败');
        }

        const successMessage = await response.text(); // 获取成功消息
        alert(successMessage);
        fetchBooks(); // 重新加载书籍列表以反映变化
    } catch (error) {
        alert(`错误: ${error.message}`);
    } finally {
        borrowButton.disabled = false; // 恢复按钮状态
    }
}

// 初始化加载书籍
fetchBooks();
