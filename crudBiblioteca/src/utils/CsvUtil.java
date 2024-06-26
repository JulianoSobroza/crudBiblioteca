package utils;

import models.Aluno;
import models.Emprestimo;
import models.Livro;
import models.Bibliotecario;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvUtil {
    public static List<Livro> lerLivros(String caminho) {
        List<Livro> livros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                livros.add(new Livro(dados[0], dados[1], dados[2], dados[3], Integer.parseInt(dados[4])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return livros;
    }

    public static List<Aluno> lerAlunos(String caminho) {
        List<Aluno> alunos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                alunos.add(new Aluno(dados[0], dados[1], dados[2], Double.parseDouble(dados[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return alunos;
    }

    public static List<Bibliotecario> lerBibliotecarios(String caminho) {
        List<Bibliotecario> bibliotecarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                bibliotecarios.add(new Bibliotecario(dados[0], dados[1], dados [2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bibliotecarios;
    }

    public static List<Emprestimo> lerEmprestimos(String caminho) {
        List<Emprestimo> emprestimos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                emprestimos.add(new Emprestimo(dados[0], dados[1], dados[2], dados[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return emprestimos;
    }

    public static void salvarEmprestimos(String caminho, List<Emprestimo> emprestimos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (Emprestimo emprestimo : emprestimos) {
                bw.write(emprestimo.toCsvString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
    public static void salvarEmprestimos(String caminhoArquivo, List<Emprestimo> emprestimos) {
        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            for (Emprestimo emprestimo : emprestimos) {
                writer.append(emprestimo.toCsvString());
                writer.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 */

    public static void adicionarLivro (String caminho, List<Livro> livros) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for ( Livro livro : livros) {
                bw.write(livro.getId() + ";" + livro.getNome() + ";" + livro.getAutor() + ";" + livro.getLancamento() + ";" + livro.getQuantidade());
                bw.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removerLivro (String caminho, List<Livro>livros){} // IMPLEMENTAR

    public static void adicionarAluno(String caminhoArquivo, List<Aluno> alunos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Aluno aluno : alunos) {
                bw.write(aluno.getNome() + ";" + aluno.getMatricula() + ";" + aluno.getSenha() + ";" + aluno.getDebito());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removerAluno(String caminhoArquivo, List<Aluno>alunos) {} // IMPLEMENTAR

}
