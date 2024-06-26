package models;
/*
import java.text.SimpleDateFormat;
import java.util.Date;

 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Emprestimo {
    private Aluno aluno;
    private Livro livro;
    private Date dataEmprestimo;
    private Date dataDevolucao;

    public Emprestimo(Aluno aluno, Livro livro, Date dataEmprestimo, Date dataDevolucao) {
        this.aluno = aluno;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

     public Emprestimo(String Aluno, String Livro, String dataEmprestimoString, String dataDevolucaoString) throws ParseException {
        this.aluno = new Aluno(Aluno); // Supondo que Aluno tenha um construtor com nome
        this.livro = new Livro(Livro); // Supondo que Livro tenha um construtor com nome
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.dataEmprestimo = sdf.parse(dataEmprestimoString);
        this.dataDevolucao = sdf.parse(dataDevolucaoString);
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "aluno=" + aluno +
                ", livro=" + livro +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucao=" + dataDevolucao +
                '}';
    }

    public String toCsvString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return aluno.getMatricula() + "," + livro.getId() + "," + sdf.format(dataEmprestimo);
    }

    //Getters e Setters
    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
