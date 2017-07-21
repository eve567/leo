package net.ufrog.leo.client.api.beans;

import java.util.*;

/**
 * 列表响应
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-03-27
 * @since 0.1
 */
public class ListResp<T extends Resp> extends Resp implements List<T> {

    private static final long serialVersionUID = 2078012406999625657L;

    /** 内容 */
    private List<T> content;

    /** 构造函数 */
    public ListResp() {
        content = new ArrayList<>();
    }

    @Override
    public int size() {
        return content.size();
    }

    @Override
    public boolean isEmpty() {
        return content.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return content.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

    @Override
    public Object[] toArray() {
        return content.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return content.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return content.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return content.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return content.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return content.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return content.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return content.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return content.retainAll(c);
    }

    @Override
    public void clear() {
        content.clear();
    }

    @Override
    public T get(int index) {
        return content.get(index);
    }

    @Override
    public T set(int index, T element) {
        return content.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        content.add(index, element);
    }

    @Override
    public T remove(int index) {
        return content.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return content.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return content.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return content.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return content.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return content.subList(fromIndex, toIndex);
    }
}
