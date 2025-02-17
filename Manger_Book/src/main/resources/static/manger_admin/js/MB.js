document.addEventListener('DOMContentLoaded', function() {
    const addBookBtn = document.getElementById('addBookBtn');
    const booksList = document.getElementById('booksList');

    // 退出登录功能
    document.getElementById('logoutLink').addEventListener('click', function(event) {
        event.preventDefault();
        localStorage.removeItem('userToken'); // 清除登录信息
        window.location.href = '../login.html'; // 跳转到登录页面
    });

    // 加载图书列表
    function loadBooks() {
        fetch('/api/books')
            .then(response => response.ok ? response.json() : Promise.reject('加载失败'))
            .then(books => {
                booksList.innerHTML = ''; // 清空现有列表
                books.forEach(book => {
                    const li = document.createElement('li');
                    li.innerHTML = `
                    <strong>${book.title}</strong> - ${book.author} (ISBN: ${book.isbn}) 
                    - 库存: ${book.quantity}
                    <button class="edit-btn" data-book-id="${book.id}">编辑</button>
                    <button class="delete-btn" data-book-id="${book.id}">删除</button>
                `;
                    booksList.appendChild(li);
                });
            })
            .catch(error => {
                console.error(error);  // 错误处理
            });
    }


    // 添加图书事件
    addBookBtn.addEventListener('click', function() {
        const title = document.getElementById('bookTitle').value.trim();
        const author = document.getElementById('bookAuthor').value.trim();
        const isbn = document.getElementById('bookISBN').value.trim();
        const quantity = parseInt(document.getElementById('bookQuantity').value.trim(), 10);

        if (title && author && isbn && !isNaN(quantity) && quantity > 0) {
            fetch('/api/books', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ title, author, isbn, quantity })
            })
                .then(response => response.ok ? response.json() : Promise.reject('添加失败'))
                .then(() => {
                    alert('图书添加成功！');
                    resetForm();
                    loadBooks(); // 重新加载图书列表
                })
                .catch(error => alert('添加图书失败: ' + error));
        } else {
            alert('请填写所有字段，且库存数量必须为正整数。');
        }
    });

    // 处理编辑和删除按钮
    booksList.addEventListener('click', function(event) {
        const bookId = event.target.getAttribute('data-book-id');

        if (event.target.classList.contains('edit-btn')) {
            // 编辑逻辑（可以弹出窗口或其他方式）
            alert(`编辑图书 ID: ${bookId}`);
        } else if (event.target.classList.contains('delete-btn')) {
            deleteBook(bookId);
        }
    });

    // 删除图书
    function deleteBook(bookId) {
        fetch(`/api/books/${bookId}`, {
            method: 'DELETE'
        })
            .then(response => response.ok ? response.json() : Promise.reject('删除失败'))
            .then(() => {
                loadBooks(); // 重新加载图书列表
                alert('图书已删除！');
            })
            .catch(error => alert('删除图书失败: ' + error));
    }

    // 重置表单
    function resetForm() {
        document.getElementById('bookTitle').value = '';
        document.getElementById('bookAuthor').value = '';
        document.getElementById('bookISBN').value = '';
        document.getElementById('bookQuantity').value = '';
    }

    // 初始加载图书列表
    loadBooks();
});
