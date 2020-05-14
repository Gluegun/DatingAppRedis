У вас есть сайт знакомств, на котором уже зарегистрировалось 20 пользователей. Показывайте их всех по очереди на главной странице сайта: изначально все пользователи упорядочены в порядке регистрации. После каждого показа пользователь отправляется в конец очереди. Иногда пользователи оплачивают специальную услугу, которая перемещает их в начало очереди. 

Напишите программу, которая будет эмулировать работу такого сайта, хранить очередь в Redis, и выводить лог операций в консоль.

Программа должна запускать бесконечный цикл, в котором:

1. Выводится в консоль номер пользователя, которого нужно сейчас отобразить на главной странице. 

2. В одном из 10 случаев, случайный пользователь оплачивает платную услугу, в консоль выводится его номер.

3. Программа ждет 1 секунду, и цикл начинается заново.

Пример вывода:

— На главной странице показываем пользователя 1
— На главной странице показываем пользователя 2
— На главной странице показываем пользователя 3
> Пользователь 8 оплатил платную услугу
— На главной странице показываем пользователя 8
— На главной странице показываем пользователя 4
… [пропущено] Показываем 5,6,7,9,10,11,12,13,14,15,16,17,18,19…
— На главной странице показываем пользователя 1 [снова]
И т.д.
