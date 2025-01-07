// Elements
const taskInput = document.getElementById('task-input');
const dueDateInput = document.getElementById('due-date');
const addTaskBtn = document.getElementById('add-task-btn');
const todoList = document.getElementById('todo-list');
const searchBar = document.getElementById('search-bar');
const sortBy = document.getElementById('sort-by');
const clearBtn = document.getElementById('clear-btn');
const darkModeIcon = document.getElementById('dark-mode-icon');

let tasks = [];

// Dark Mode Toggle with Icon Click
darkModeIcon.addEventListener('click', () => {
    document.body.classList.toggle('dark-mode');
    document.querySelector('.todo-card').classList.toggle('dark-mode');
    darkModeIcon.classList.toggle('fa-sun');
    darkModeIcon.classList.toggle('fa-moon');
});

// Add Task
addTaskBtn.addEventListener('click', () => {
    const taskText = taskInput.value.trim();
    const dueDate = dueDateInput.value;

    if (!taskText) {
        alert('Please enter a task.');
        return;
    }

    tasks.push({ text: taskText, dueDate: dueDate || 'No due date' });
    taskInput.value = '';
    dueDateInput.value = '';
    renderTasks();
});

// Render Tasks
function renderTasks() {
    todoList.innerHTML = '';
    let filteredTasks = tasks.filter(task => task.text.toLowerCase().includes(searchBar.value.toLowerCase()));

    if (sortBy.value === 'date-asc') {
        filteredTasks.sort((a, b) => new Date(a.dueDate) - new Date(b.dueDate));
    } else if (sortBy.value === 'date-desc') {
        filteredTasks.sort((a, b) => new Date(b.dueDate) - new Date(a.dueDate));
    }

    filteredTasks.forEach((task, index) => {
        const taskItem = document.createElement('li');
        taskItem.classList.add('todo-item');

        const taskText = document.createElement('span');
        taskText.classList.add('task-text');
        taskText.textContent = task.text;

        const taskMeta = document.createElement('div');
        taskMeta.classList.add('todo-meta');
        taskMeta.textContent = task.dueDate;

        const taskActions = document.createElement('div');
        taskActions.classList.add('todo-actions');

        const deleteBtn = document.createElement('button');
        deleteBtn.textContent = 'Delete';
        deleteBtn.addEventListener('click', () => {
            tasks.splice(index, 1); // Remove task from array
            renderTasks(); // Re-render task list
        });

        taskActions.appendChild(deleteBtn);
        taskItem.appendChild(taskText);
        taskItem.appendChild(taskMeta);
        taskItem.appendChild(taskActions);

        todoList.appendChild(taskItem);
    });
}

// Search Tasks
searchBar.addEventListener('input', renderTasks);

// Clear All Tasks
clearBtn.addEventListener('click', () => {
    tasks = [];
    renderTasks();
});
