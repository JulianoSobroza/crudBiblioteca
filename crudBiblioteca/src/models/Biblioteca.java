package models;

import utils.CsvUtil;

import java.util.Date;
import java.util.List;

public class Biblioteca {
    private List<Livro> livros;
    private List<Aluno> alunos;
    private List<Bibliotecario> bibliotecarios;
    private List<Emprestimo> emprestimos;

    public Biblioteca(List<Livro> livros, List<Aluno> alunos, List<Bibliotecario> bibliotecarios, List<Emprestimo> emprestimos) {
        this.livros = livros;
        this.alunos = alunos;
        this.bibliotecarios = bibliotecarios;
        this.emprestimos = emprestimos;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public List<Bibliotecario> getBibliotecarios() {
        return bibliotecarios;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    public void removerLivro(Livro livro) {
        livros.remove(livro);
    }

    public void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    public void removerAluno(Aluno aluno) {
        alunos.remove(aluno);
    }

    public void emprestimoLivro(String matriculaAluno, String idLivro){
        Aluno aluno = alunos.stream()
                .filter(a -> a.getMatricula().equals(matriculaAluno))
                .findFirst()
                .orElse(null);

        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        Livro livro = livros.stream()
                .filter(l -> l.getId().equals(idLivro) && l.isDisponibilidade())
                .findFirst()
                .orElse(null);

        if (livro == null) {
            System.out.println("Livro não disponível.");
            return;
        }

        livro.setQuantidade(livro.getQuantidade() - 1);
        livro.setDisponibilidade(livro.getQuantidade() > 0);
        aluno.emprestarLivro(livro);

        Date dataEmprestimo = new Date();
        Date dataDevolucao = new Date(dataEmprestimo.getTime() + (7 * 24 * 60 * 60 * 1000)); // 7 dias depois

        Emprestimo emprestimo = new Emprestimo(aluno, livro, dataEmprestimo, dataDevolucao);

        emprestimos.add(emprestimo);

        CsvUtil.salvarEmprestimos("/home/azorbos/IntellijProjects/crudBiblioteca/data/emprestimos.csv", emprestimos);
        System.out.println("Empréstimo realizado com sucesso.");
    }

}
