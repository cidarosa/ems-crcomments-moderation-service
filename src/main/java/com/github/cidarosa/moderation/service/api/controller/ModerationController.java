package com.github.cidarosa.moderation.service.api.controller;

import com.github.cidarosa.moderation.service.api.model.ModerationInputDTO;
import com.github.cidarosa.moderation.service.api.model.ModerationOutputDTO;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/moderate")
public class ModerationController {


    private final Set<String> PROHIBITED_WORDS = new HashSet<>(); // Set.of("ódio", "xingamento");
    private Set<String> wordsFound = new HashSet<>();

    public ModerationController() {
        this.PROHIBITED_WORDS.add("ódio");
        this.PROHIBITED_WORDS.add("xingamento");
    }

    @PostMapping
    public ModerationOutputDTO comments(@RequestBody ModerationInputDTO input) {

        wordsFound.clear();

        String inputText = input.getText();
        String inputTextNormalized = inputText.toLowerCase()
                .replaceAll("[^a-z0-9áéíóúçàèìòùãõâêîôû ]", " ");
        String[] words = inputTextNormalized.split("\\s+");

        for (String word : words) {
            if (PROHIBITED_WORDS.contains(word)) {
                wordsFound.add(word);
            }
        }

        if (!wordsFound.isEmpty()) {
            return ModerationOutputDTO.builder()
                    .approved(false)
                    .reason("Contém a(s) palavra(s) proibida(s): " + wordsFound)
                    .build();
        }

        return ModerationOutputDTO.builder()
                .approved(true)
                .reason("Comentário aprovado. Não contém palavras proibidas")
                .build();
    }
}
