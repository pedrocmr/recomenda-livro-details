package com.recomendalivrodetails.entities.front;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class BookDetails {

    String idLivro;
    String nomeLivro;
    String resumo;
    Integer anoLancamento;
    String imagemUrl;
}
