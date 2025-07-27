package br.com.bootcamp;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Cria cursos
        Curso curso1 = new Curso();
        curso1.setTitulo("Java Básico");
        curso1.setDescricao("Fundamentos de Java");
        curso1.setCargaHoraria(8);

        Curso curso2 = new Curso();
        curso2.setTitulo("Spring Boot");
        curso2.setDescricao("APIs com Spring Boot");
        curso2.setCargaHoraria(10);

        // Cria mentoria
        Mentoria mentoria = new Mentoria();
        mentoria.setTitulo("Mentoria de Carreira");
        mentoria.setDescricao("Como se destacar no mercado");
        mentoria.setData(LocalDate.now());

        // Configura bootcamp
        Bootcamp bootcamp = new Bootcamp();
        bootcamp.setNome("Bootcamp Java Developer");
        bootcamp.setDescricao("Bootcamp intensivo Java");
        bootcamp.getConteudos().add(curso1);
        bootcamp.getConteudos().add(curso2);
        bootcamp.getConteudos().add(mentoria);

        // Simula Dev Camila
        Dev camila = new Dev();
        camila.setNome("Camila");
        camila.inscreverBootcamp(bootcamp);
        camila.progredir();
        camila.progredir();
        System.out.println("Camila Inscritos: " + camila.getConteudosInscritos());
        System.out.println("Camila Concluídos: " + camila.getConteudosConcluidos());
        System.out.println("XP: " + camila.calcularTotalXp());

        // Simula Dev João
        Dev joao = new Dev();
        joao.setNome("João");
        joao.inscreverBootcamp(bootcamp);
        joao.progredir();
        System.out.println("João Inscritos: " + joao.getConteudosInscritos());
        System.out.println("João Concluídos: " + joao.getConteudosConcluidos());
        System.out.println("XP: " + joao.calcularTotalXp());
    }
} 




