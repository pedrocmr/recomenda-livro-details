package com.recomendalivrodetails.entities.front;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class BookDetails {

    String idLivro;
    String nomeLivro;
    String resumo;
    Integer anoLancamento;
    String imagemUrl;
    List<String> authors;
}
