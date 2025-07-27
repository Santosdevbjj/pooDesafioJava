package br.com.bootcamp;

public abstract class Conteudo {
    // XP base comum para todos os conteúdos
    protected static final double XP_PADRAO = 10d;

    private String titulo;
    private String descricao;

    // Abstração: cada tipo calcula XP de forma diferente
    public abstract double calcularXp();

    // Encapsulamento: acesso controlado
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
} 

