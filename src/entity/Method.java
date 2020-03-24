package entity;

import jaxb.MethodType;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Method {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;
    private Integer step;
    private String content;

    public Method() {
    }

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID", nullable = false)
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "STEP", nullable = true)
    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Basic
    @Column(name = "CONTENT", nullable = true, length = 1000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Method method = (Method) o;
        return id == method.id &&
                Objects.equals(step, method.step) &&
                Objects.equals(content, method.content);
    }

    public MethodType toMethodType() {
        MethodType methodType = new MethodType();
        methodType.setStep(this.step);
        methodType.setContent(this.content);
        return methodType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, step, content);
    }
}
