import models.*;
import utils.CsvUtil;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static utils.CsvUtil.lerEmprestimos;

public class Main {
    public static void main(String[] args) {
        List<Livro> livros = CsvUtil.lerLivros("/home/azorbos/IntellijProjects/crudBiblioteca/data/livros.csv");
        List<Aluno> alunos = CsvUtil.lerAlunos("/home/azorbos/IntellijProjects/crudBiblioteca/data/alunos.csv");
        List<Bibliotecario> bibliotecarios = CsvUtil.lerBibliotecarios("/home/azorbos/IntellijProjects/crudBiblioteca/data/bibliotecarios.csv");
        List<Emprestimo> emprestimos = lerEmprestimos("/home/azorbos/IntellijProjects/crudBiblioteca/data/emprestimos.csv");

        Biblioteca biblioteca = new Biblioteca(livros, alunos, bibliotecarios, emprestimos);
        Scanner scanner = new Scanner(System.in);
        Date date = new Date();

        lerEmprestimos("/home/azorbos/IntellijProjects/crudBiblioteca/data/emprestimos.csv");
        listarEmprestimos(biblioteca);

        while (true) {
            System.out.println("\n========== Biblioteca ==========");
            System.out.println("[1] Login Aluno");
            System.out.println("[2] Login Bibliotecário");
            System.out.println("================================");
            System.out.println("[3] Sair");
            System.out.println("[4] Empréstimo de Livro");
            //System.out.println("4 LISTA EMPRESTIMOS");
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
                    break;

                case 4:

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

        menuAluno(scanner, biblioteca, aluno);
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

        menuBibliotecario(scanner, biblioteca, bibliotecario);
    }

    private static void menuBibliotecario(Scanner scanner, Biblioteca biblioteca, Bibliotecario bibliotecario) {
        while (true) {
            System.out.println("Bem vindo(a) " + bibliotecario.getNome());

            System.out.println("[1] Adicionar livro");
            System.out.println("[2] Remover livro");
            System.out.println("[3] Listar livros");

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
                    System.out.println("Digite as informações do aluno separado por espaço(Nome, Matrícula, Senha):");
                    String[] alunoDados = scanner.nextLine().split(" ");
                    double debitoInicial = 0;
                    Aluno novoAluno = new Aluno(alunoDados[0], alunoDados[1], alunoDados[2], debitoInicial);
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
                        CsvUtil.adicionarAluno("/home/azorbos/IntellijProjects/crudBiblioteca/data/alunos.csv", biblioteca.getAlunos());
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

    private static void menuAluno(Scanner scanner, Biblioteca biblioteca, Aluno aluno) {
        while (true) {
            System.out.println("\n========== Biblioteca ==========");
            System.out.println("Bem vindo(a) " + aluno.getNome());
            System.out.println("================================");
            if (aluno.temDebito()) {
                System.out.printf("VOCÊ POSSUI UM DÉBITO DE R$%.2f\n", aluno.getDebito());
                System.out.println("================================");
            }
            System.out.println("[1] Listar todos os livros da Biblioteca");
            System.out.println("\n[2] Realizar Empréstimo");
            System.out.println("[3] Fazer devolução");
            System.out.println("\n[4] Meus Livros");
            System.out.println("[5] Pagar débito");
            System.out.println("\n[6] Voltar");
            System.out.println("7 fazer imprestimo metodo biblioteca");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1: // Mostrar TODA a biblioteca
                    listarLivros(biblioteca);
                    break;

                case 2: // Fazer empréstimo
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
                        biblioteca.emprestimoLivro(aluno.getMatricula(), emprestimoLivroId);
                        //aluno.emprestarLivro(livroParaEmprestimo); // Adicionar livro ao aluno
                        System.out.println("Você realizou o empréstimo do livro " + livroParaEmprestimo.getNome());
                    } else {
                        System.out.println("Livro não disponível.");
                    }
                    break;

                case 3: // Fazer devolução
                    System.out.println("Digite o ID do livro para devolução:");
                    String devolucaoLivroId = scanner.nextLine();
                    Livro livroParaDevolucao = biblioteca.getLivros().stream()
                            .filter(l -> l.getId().equals(devolucaoLivroId))
                            .findFirst()
                            .orElse(null);
                    if (livroParaDevolucao != null) {
                        livroParaDevolucao.devolver();
                        aluno.devolverLivro(livroParaDevolucao);
                        System.out.println("Você devolveu o livro " + livroParaDevolucao.getNome());
                    } else {
                        System.out.println("Livro não encontrado.");
                    }
                    break;

                case 4: // Livros do aluno
                    System.out.println("\nSeus Livros:");
                    if (aluno.getLivros().isEmpty()) {
                        System.out.println("Você não possui livros emprestados.");
                    } else {
                        for (Livro livro : aluno.getLivros()) {
                            System.out.println(livro.getNome() + " - ID: " + livro.getId());
                        }
                    }
                    break;

                case 5:  // Pagar débito
                    System.out.printf("\nSeu débito: R$%.2f", aluno.getDebito());
                    System.out.println("\nDigite o valor a ser pago:");
                    double valor = scanner.nextDouble();
                    scanner.nextLine(); // Consumir a nova linha
                    aluno.pagarDebito(valor);
                    System.out.println("Débito pago com sucesso.");
                    break;

                case 6:
                    return;

                case 7:
                    System.out.print("ID do Livro: ");
                    String idLivro = scanner.nextLine();
                    biblioteca.emprestimoLivro(aluno.getMatricula(), idLivro);
                    break;

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


    private static void listarEmprestimos(Biblioteca biblioteca) {
        for (Emprestimo emprestimo : biblioteca.getEmprestimos()) {
            System.out.println(emprestimo.getAluno());
            System.out.println(emprestimo.getLivro());
            System.out.println(emprestimo.getDataEmprestimo());
            System.out.println(emprestimo.getDataDevolucao());
            //System.out.println("ID livro: " + "Livro: " + emprestimo.getLivro() + ", nome Aluno: " + emprestimo.getAluno() +
            //        ", Data Retirada: " + emprestimo.getDataEmprestimo() + ", Data Devolução: " + emprestimo.getDataDevolucao());
        }
    }
}
