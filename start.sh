#!/usr/bin/env bash
if [ ! -d /var/lib/mysql/mysql ]; then
    # Выставляем правильные права доступа
    chown mysql:mysql /var/lib/mysql

    # Инициализируем системные таблицы
    mysql_install_db
fi