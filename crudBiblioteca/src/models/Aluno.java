package models;

import java.util.ArrayList;
import java.util.List;

public class Aluno {
    private final String nome;
    private final String matricula;
    private String senha;
    private double debito;
    private List<Livro> livrosEmprestados;

    public Aluno(String nome, String matricula, String senha, double debito) {
        this.nome = nome;
        this.matricula = matricula;
        this.senha = senha;
        this.debito = debito;
        this.livrosEmprestados = new ArrayList<>();
    }

    public Aluno(String nome) {
        this.nome = nome;
        this.matricula = ""; // ou inicialize conforme necessário
        this.livrosEmprestados = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "nome='" + nome + '\'' +
                ", matricula='" + matricula + '\'' +
                ", senha='" + senha + '\'' +
                ", debito=" + debito +
                '}';
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public double getDebito() {
        return debito;
    }

    public void adicionarDebito(double valor) {
        this.debito += valor;
    }

    public void pagarDebito(double valor) {
        this.debito -= valor;
    }

    public boolean temDebito() {
        return debito > 0;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void emprestarLivro(Livro livro) {
        livrosEmprestados.add(livro);
    }

    public void devolverLivro(Livro livro) {
        livrosEmprestados.remove(livro);
    }

    public List<Livro> getLivros() {
        return livrosEmprestados;
    }
}
