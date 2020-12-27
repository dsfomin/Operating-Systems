import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reference<T> {

    private T value;

    public Reference() {
        this.value = null;
    }
}
