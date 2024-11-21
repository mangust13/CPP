import java.util.List;

public interface Repository<T> {
    T get(Long id);
    List<T> getAll();
    void save(T entity);
    void update(T entity);
    void delete(T entity);
}