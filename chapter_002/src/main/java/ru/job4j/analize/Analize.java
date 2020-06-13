package ru.job4j.analize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Analize {
    private Info info = new Info();

    /**
     * метод должен возвращать статистику об изменении коллекции.
     *
     * @param previous previous List
     * @param current  current List
     * @return info
     */
    public Info diff(final List<User> previous, final List<User> current) {
        Map<User, User> userMap = listInMap(current);
        for (User prev : previous) {
            User key = userMap.get(prev);
            if (key == null) {
                info.deleted += 1;
                continue;
            } else if (key.id == prev.id && !key.name.equals(prev.name)) {
                info.changed += 1;
            }
            userMap.remove(prev);
        }
        info.added += userMap.size();
        return info;
    }

    /**
     * Перенести List в Map.
     *
     * @param list List<E>
     * @param <E>  тип
     * @return Map<E, Object>
     */
    public <E> Map<E, E> listInMap(final List<E> list) {
        Map<E, E> map = new HashMap<>(Math.max((int) (list.size() / .75f) + 1, 16));
        for (E e : list) {
            map.put(e, e);
        }
        return map;
    }

    public static class User {
        private int id;
        private String name;

        public User(final int id, final String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * @param o сравнить c User
         * @return boolean
         */
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            User user = (User) o;
            return id == user.id;
        }

        /**
         * hashCode только для id.
         *
         * @return hashCode
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(this.id);
        }

        /**
         * @return User String.
         */
        @Override
        public String toString() {
            return "User{"
                    + " id=" + id
                    + ", name='" + name + '\''
                    + '}';
        }
    }


    public static class Info {
        private int added;
        private int changed;
        private int deleted;

        /**
         * @return User String.
         */
        @Override
        public String toString() {
            return "Info{"
                    + "added=" + added
                    + ", changed=" + changed
                    + ", deleted=" + deleted
                    + '}';
        }

        public final int getAdded() {
            return added;
        }

        public final int getChanged() {
            return changed;
        }

        public final int getDeleted() {
            return deleted;
        }
    }
}