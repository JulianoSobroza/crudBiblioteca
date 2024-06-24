import models.*;
import utils.CsvUtil;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Livro> livros = CsvUtil.lerLivros("/home/azorbos/IntellijProjects/crudBiblioteca/data/livros.csv");
        List<Aluno> alunos = CsvUtil.lerAlunos("/home/azorbos/IntellijProjects/crudBiblioteca/data/alunos.csv");
        List<Bibliotecario> bibliotecarios = CsvUtil.lerBibliotecarios("/home/azorbos/IntellijProjects/crudBiblioteca/data/bibliotecarios.csv");

        Biblioteca biblioteca = new Biblioteca(livros, alunos, bibliotecarios);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("[1] Login Aluno");
            System.out.println("[2] Login Bibliotecário");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    loginAluno(scanner, biblioteca);
                    break;

                case 2:
                    loginBibliotecario(scanner, biblioteca);
                    break;

                case 3:
                    System.exit(0);
                    listarLivros(biblioteca);
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    private static void loginAluno(Scanner scanner, Biblioteca biblioteca) {
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Aluno aluno = biblioteca.getAlunos().stream()
                .filter(a -> a.getMatricula().equals(matricula) && a.getSenha().equals(senha))
                .findFirst()
                .orElse(null);

        if (aluno == null) {
            System.out.println("Aluno não encontrado ou senha incorreta.");
            return;
        }

        alunoMenu(scanner, biblioteca, aluno);
    }

    private static void loginBibliotecario(Scanner scanner, Biblioteca biblioteca) {
        System.out.print("Id: ");
        String id = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Bibliotecario bibliotecario = biblioteca.getBibliotecarios().stream()
                .filter(b -> b.getId().equals(id) && b.getSenha().equals(senha))
                .findFirst()
                .orElse(null);

        if (bibliotecario == null) {
            System.out.println("Bibliotecário não encontrado ou senha incorreta.");
            return;
        }

        bibliotecarioMenu(scanner, biblioteca, bibliotecario);
    }

    private static void bibliotecarioMenu(Scanner scanner, Biblioteca biblioteca, Bibliotecario bibliotecario) {
        while (true) {
            System.out.println("Bem vindo(a) " + bibliotecario.getNome());

            System.out.println("[1] Adicionar livro");
            System.out.println("[2] Remover livro");
            System.out.println("[3] Listar livros]");

            System.out.println("\n[4] Adicionar aluno");
            System.out.println("[5] Remover aluno");
            System.out.println("[6] Listar alunos");

            System.out.println("\n[7] Voltar");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    System.out.println("Digite as informações do livro separado por espaço(ID, Nome, Autor, Lançamento, Quantidade):");
                    String[] livroDados = scanner.nextLine().split(" ");
                    Livro novoLivro = new Livro(livroDados[0], livroDados[1], livroDados[2], livroDados[3], Integer.parseInt(livroDados[4]));
                    System.out.println("Livro \"" + novoLivro.getNome() + "\"adicionado com sucesso. \nSeu Id: " + novoLivro.getId());
                    biblioteca.adicionarLivro(novoLivro);
                    CsvUtil.adicionarLivro("/home/azorbos/IntellijProjects/crudBiblioteca/data/livros.csv", biblioteca.getLivros());
                    break;

                case 2:
                    System.out.print("Digite o ID do livro a ser removido:");
                    String livroId = scanner.nextLine();
                    Livro livro = biblioteca.getLivros().stream()
                            .filter(l -> l.getId().equals(livroId))
                            .findFirst()
                            .orElse(null);
                    if (livro != null) {
                        System.out.println("Livro " + livro.getNome() + " removido com sucesso.");
                        biblioteca.removerLivro(livro);
                        CsvUtil.adicionarLivro("/home/azorbos/IntellijProjects/crudBiblioteca/data/livros.csv", biblioteca.getLivros());
                    } else {
                        System.out.println("Livro não encontrado.");
                    }
                    break;

                case 3:
                    listarLivros(biblioteca);
                    break;

                case 4:
                    System.out.println("Digite as informações do aluno separado por espaço(Nome, Matrícula, Senha, Débito):");
                    String[] alunoDados = scanner.nextLine().split(" ");
                    Aluno novoAluno = new Aluno(alunoDados[0], alunoDados[1], alunoDados[2], Double.parseDouble(alunoDados[3]));
                    biblioteca.adicionarAluno(novoAluno);
                    CsvUtil.adicionarAluno("/home/azorbos/IntellijProjects/crudBiblioteca/data/alunos.csv", biblioteca.getAlunos());
                    System.out.println("Aluno " + novoAluno.getNome() + " adicionado com sucesso.");
                    break;

                case 5:
                    System.out.println("Digite a matrícula do aluno a ser removido:");
                    String matriculaAluno = scanner.nextLine();
                    Aluno aluno = biblioteca.getAlunos().stream()
                            .filter(a -> a.getMatricula().equals(matriculaAluno))
                            .findFirst()
                            .orElse(null);
                    if (aluno != null) {
                        biblioteca.removerAluno(aluno);
                        CsvUtil.adicionarAluno("data/alunos.csv", biblioteca.getAlunos());
                        System.out.println("Aluno " + aluno.getNome() + " removido com sucesso.");
                    } else {
                        System.out.println("Aluno não encontrado.");
                    }
                    break;

                case 6:
                    listarAlunos(biblioteca);
                    break;

                case 7:
                    return;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    private static void alunoMenu(Scanner scanner, Biblioteca biblioteca, Aluno aluno) {
        while (true) {
            System.out.println("[1] Listar Livros");
            System.out.println("[2] Realizar Empréstimo");
            System.out.println("[3] Devolver Livro");
            System.out.println("[4] Pagar débito");
            System.out.println("[5] Voltar");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    listarLivros(biblioteca);
                    break;
                case 2:
                    if (aluno.temDebito()) {
                        System.out.println("Você tem débito pendente. Pague o débito antes de fazer um empréstimo.");
                        break;
                    }
                    System.out.println("Digite o ID do livro para empréstimo:");
                    String emprestimoLivroId = scanner.nextLine();
                    Livro livroParaEmprestimo = biblioteca.getLivros().stream()
                            .filter(l -> l.getId().equals(emprestimoLivroId))
                            .findFirst()
                            .orElse(null);
                    if (livroParaEmprestimo != null && livroParaEmprestimo.isDisponivelBool()) {
                        livroParaEmprestimo.emprestar();
                        System.out.println("Livro emprestado com sucesso.");
                    } else {
                        System.out.println("Livro não disponível.");
                    }
                    break;
                case 3:
                    System.out.println("Digite o ID do livro para devolução:");
                    String devolucaoLivroId = scanner.nextLine();
                    Livro livroParaDevolucao = biblioteca.getLivros().stream()
                            .filter(l -> l.getId().equals(devolucaoLivroId))
                            .findFirst()
                            .orElse(null);
                    if (livroParaDevolucao != null) {
                        livroParaDevolucao.devolver();
                        System.out.println("Livro devolvido com sucesso.");
                    } else {
                        System.out.println("Livro não encontrado.");
                    }
                    break;
                case 4:
                    System.out.println("Digite o valor a ser pago:");
                    double valor = scanner.nextDouble();
                    scanner.nextLine(); // Consumir a nova linha
                    aluno.pagarDebito(valor);
                    System.out.println("Débito pago com sucesso.");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    private static void listarLivros(Biblioteca biblioteca) {
        for (Livro livro : biblioteca.getLivros()) {
            System.out.println("ID: " + livro.getId() + ", Nome: " + livro.getNome() + ", Autor: " + livro.getAutor() + ", Disponível: " + livro.isDisponivelBool());
        }
    }

    private static void listarAlunos(Biblioteca biblioteca) {
        for (Aluno aluno : biblioteca.getAlunos()) {
            System.out.println("Nome: " + aluno.getNome() + ", Matricula: " + aluno.getMatricula() + ", Débito: R$" + aluno.getDebito());
        }
    }
}
