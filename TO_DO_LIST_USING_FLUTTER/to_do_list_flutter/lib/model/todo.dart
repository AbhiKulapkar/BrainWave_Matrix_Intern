class Todo {
  String? id;
  String? todoText;
  bool? isDone;

  ToDo({
    required this.id,
    required this.todoText,
    this.isDone = false,
  });


  static List<ToDo> todoList()
  {
    return [
      ToDo(id: '01', todoText: 'Buy Groceries', isDone: True),
      ToDo(id: '02', todoText: 'diet', isDone: True),
      ToDo(id: '03', todoText: 'Workout',),
      ToDo(id: '04', todoText: 'Read Books',),
      ToDo(id: '05', todoText: 'Watch Movies',),
      ToDo(id: '06', todoText: 'Cook Food',),
    ];
  },

}
