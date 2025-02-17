document.addEventListener("DOMContentLoaded", function() {
    loadBooks();
    loadUsers();
    loadBorrowRecords();
});

function loadBooks() {
    fetch('/api/books')
        .then(response => {
            if (!response.ok) throw new Error('加载失败');
            return response.json();
        })
        .then(books => {
            const booksList = document.getElementById('booksList');
            if (!booksList) throw new Error('未找到图书列表元素');
            booksList.innerHTML = '';
            books.slice(0, 10).forEach(book => {
                const li = document.createElement('li');
                li.textContent = `${book.title} - ${book.author}`;
                booksList.appendChild(li);
            });
        })
        .catch(error => {
            alert('加载图书失败: ' + error.message);
        });
}

function loadUsers() {
    fetch('/api/users')
        .then(response => {
            if (!response.ok) throw new Error('加载失败');
            return response.json();
        })
        .then(users => {
            const usersList = document.getElementById('usersList');
            if (!usersList) throw new Error('未找到用户列表元素');
            usersList.innerHTML = '';
            users.slice(0, 10).forEach(user => {
                const li = document.createElement('li');
                li.textContent = `${user.username}`;
                usersList.appendChild(li);
            });
        })
        .catch(error => {
            alert('加载用户失败: ' + error.message);
        });
}

function loadBorrowRecords() {
    fetch('/api/borrow')
        .then(response => {
            if (!response.ok) throw new Error('加载借阅记录失败');
            return response.json();
        })
        .then(records => {
            const borrowRecordsList = document.getElementById('borrowRecordsList');
            if (!borrowRecordsList) throw new Error('未找到借阅记录列表元素');
            borrowRecordsList.innerHTML = '';
            records.slice(0, 10).forEach(record => {
                const li = document.createElement('li');
                li.textContent = `用户 ID: ${record.userId} 借阅图书 ID: ${record.bookId} 借阅日期: ${record.borrowDate}`;
                borrowRecordsList.appendChild(li);
            });
        })
        .catch(error => {
            alert('加载借阅记录失败: ' + error.message);
        });
}
