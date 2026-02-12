package com.example.saudejapostosaudeservice.infrastructure.input.api.controllers.postosaude;

import com.example.saudejapostosaudeservice.controllers.PostoSaudeController;
import com.example.saudejapostosaudeservice.datasources.NotificacaoDataSource;
import com.example.saudejapostosaudeservice.datasources.PostoSaudeDataSource;
import com.example.saudejapostosaudeservice.datasources.SolicitacaoVinculoPacientePostoSaudeDataSource;
import com.example.saudejapostosaudeservice.datasources.UsuarioDataSource;
import dtos.requests.*;
import dtos.responses.*;
import enums.EstadoEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/postos-saude")
@Tag(name = "Postos de Saúde API V1", description = "Versão 1 do controlador referente a postos de saúde")
@Profile("api")
public class PostoSaudeControllerV1 {

    private final PostoSaudeController postoSaudeController;

    public PostoSaudeControllerV1(PostoSaudeDataSource postoSaudeDataSource, SolicitacaoVinculoPacientePostoSaudeDataSource solicitacaoVinculoPacientePostoSaudeDataSource, UsuarioDataSource usuarioDataSource, NotificacaoDataSource notificacaoDataSource) {
        this.postoSaudeController = new PostoSaudeController(postoSaudeDataSource, solicitacaoVinculoPacientePostoSaudeDataSource, usuarioDataSource, notificacaoDataSource);
    }

    @Operation(summary = "Cria um posto de saúde",
            description = "Endpoint restrito a usuários ADMIN",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Posto de saúde criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostoSaudeResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do posto de saúde a ser criado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'ADMIN'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PostMapping
    public ResponseEntity<PostoSaudeResponse> criarPostoSaude(@AuthenticationPrincipal Jwt jwt,
                                                        @RequestBody @Valid CriarPostoSaudeRequest criarPostoSaudeRequest) {
        log.info("Admin {} criando posto de saúde {}", jwt.getSubject(), criarPostoSaudeRequest.nome());
        PostoSaudeResponse postoSaudeResponse = postoSaudeController.criarPostoSaude(criarPostoSaudeRequest);
        log.info("Admin {} criou posto de saúde {}", jwt.getSubject(), criarPostoSaudeRequest.nome());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postoSaudeResponse);
    }

    @Operation(summary = "Atualiza posto de saúde",
            description = "Endpoint restrito a usuários GERENTE",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Posto de saúde atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostoSaudeResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do posto de saúde a ser atualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'GERENTE'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Posto de saúde não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PutMapping("/{postoSaudeId}")
    public ResponseEntity<PostoSaudeResponse> atualizarPostoSaude(@AuthenticationPrincipal Jwt jwt, @PathVariable("postoSaudeId") Long postoSaudeId,
                                                                      @RequestBody @Valid AtualizarPostoSaudeRequest atualizarPostoSaudeRequest) {
        log.info("Gerente {} atualizando o posto de saúde {}", jwt.getSubject(), postoSaudeId);
        PostoSaudeResponse postoSaudeResponse = postoSaudeController.atualizarPostoSaude(Long.valueOf(jwt.getSubject()), postoSaudeId, atualizarPostoSaudeRequest);
        log.info("Gerente {} atualizou o posto de saúde {}", jwt.getSubject(), postoSaudeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postoSaudeResponse);
    }

    @Operation(summary = "Apaga posto de saúde",
            description = "Endpoint restrito a usuários GERENTE",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Posto de saúde apagado com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do posto de saúde a ser criado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'GERENTE'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Posto de saúde não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @DeleteMapping("/{postoSaudeId}")
    public ResponseEntity<Void> apagarPostoSaude(@AuthenticationPrincipal Jwt jwt, @PathVariable("postoSaudeId") Long postoSaudeId) {
        log.info("Gerente {} apagando posto de saúde {}", jwt.getSubject(), postoSaudeId);
        postoSaudeController.apagarPostoSaude(Long.valueOf(jwt.getSubject()), postoSaudeId);
        log.info("Gerente {} apagou posto de saúde {}", jwt.getSubject(), postoSaudeId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Recupera postos de saúde através de sua cidade e estado",
            description = "Endpoint liberado a qualquer usuário ou serviço autenticado",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Postos de saúde recuperados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostoSaudePageResponse.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @GetMapping
    public ResponseEntity<PostoSaudePageResponse> getPostoSaude(@AuthenticationPrincipal Jwt jwt,
                                                                                  @RequestParam int page,
                                                                                  @RequestParam int size,
                                                                                  @RequestParam boolean ord,
                                                                                  @RequestParam(required = false) String cidade,
                                                                                  @RequestParam(required = false) EstadoEnum estado) {
        log.info("Usuário ou serviço {} recuperando postos de saúde através de sua cidade e estado", jwt.getSubject());
        PostoSaudePageResponse postoSaudePageResponse = postoSaudeController.getPostoSaude(page, size, ord, cidade, estado);
        log.info("Usuário ou serviço {} recuperou postos de saúde através de sua cidade e estado", jwt.getSubject());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postoSaudePageResponse);
    }

    @Operation(summary = "Recupera pacientes de um posto de saúde",
            description = "Endpoint restrito a profissionais da saúde",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Pacientes recuperados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteIdPageResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Profissional da saúde não pertence ao posto de saúde informado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é profissional da saúde",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Posto de saúde não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @GetMapping("/pacientes/{postoSaudeId}")
    public ResponseEntity<PacienteIdPageResponse> getPacienteList(@AuthenticationPrincipal Jwt jwt,
                                                                                    @RequestParam int page,
                                                                                    @RequestParam int size,
                                                                                    @RequestParam boolean ord,
                                                                                    @PathVariable("postoSaudeId") Long postoSaudeId) {
        log.info("Profissional da saúde {} recuperando pacientes do posto de saúde {}", jwt.getSubject(), postoSaudeId);
        PacienteIdPageResponse pacienteIdPageResponse = postoSaudeController.getPacienteList(page, size, ord, Long.valueOf(jwt.getSubject()), postoSaudeId);
        log.info("Profissional da saúde {} recuperando pacientes do posto de saúde {}", jwt.getSubject(), postoSaudeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pacienteIdPageResponse);
    }

    @Operation(summary = "Recupera profissionais da saúde de um posto de saúde",
            description = "Endpoint liberado a qualquer usuário ou serviço autenticado",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Profissionais da saúde recuperados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfissionalSaudeIdPageResponse.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Posto de saúde não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @GetMapping("/profissionais-saude/{postoSaudeId}")
    public ResponseEntity<ProfissionalSaudeIdPageResponse> getProfissionalSaudeList(@AuthenticationPrincipal Jwt jwt,
                                                                @RequestParam int page,
                                                                @RequestParam int size,
                                                                @RequestParam boolean ord,
                                                                @PathVariable("postoSaudeId") Long postoSaudeId) {
        log.info("Usuário ou serviço {} recuperando profissionais da saúde do posto de saúde {}", jwt.getSubject(), postoSaudeId);
        ProfissionalSaudeIdPageResponse profissionalSaudeIdPageResponse = postoSaudeController.getProfissionalSaudeList(page, size, ord, postoSaudeId);
        log.info("Usuário ou serviço {} recuperou profissionais da saúde do posto de saúde {}", jwt.getSubject(), postoSaudeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(profissionalSaudeIdPageResponse);
    }

    @Operation(summary = "Remove um paciente de um posto de saúde",
            description = "Endpoint restrito a usuários AGENTE_COMUNITARIO",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Paciente removido com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Agente comunitário não pertence ao posto de saúde informado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'AGENTE_COMUNITARIO'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Posto de saúde não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PutMapping("/pacientes/remover/{pacienteId}/{postoSaudeId}")
    public ResponseEntity<Void> removerPaciente(@AuthenticationPrincipal Jwt jwt,
                                                @PathVariable("pacienteId") Long pacienteId,
                                                @PathVariable("postoSaudeId") Long postoSaudeId) {
        log.info("Agente comunitário {} removendo paciente {} do posto de saúde {}", jwt.getSubject(), pacienteId, postoSaudeId);
        postoSaudeController.removerPaciente(Long.valueOf(jwt.getSubject()), pacienteId, postoSaudeId);
        log.info("Agente comunitário {} removeu paciente {} do posto de saúde {}", jwt.getSubject(), pacienteId, postoSaudeId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Vincula um profissional da saúde a um posto de saúde",
            description = "Endpoint restrito a usuários GERENTE",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Profissional da saúde vinculado com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Gerente não pertence ao posto de saúde informado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'GERENTE'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Posto de saúde não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PutMapping("/profissionais-saude/vincular/{profissionalSaudeId}/{postoSaudeId}")
    public ResponseEntity<Void> vincularProfissionalSaudePostoSaude(@AuthenticationPrincipal Jwt jwt,
                                                                   @PathVariable("profissionalSaudeId") Long profissionalSaudeId,
                                                                   @PathVariable("postoSaudeId") Long postoSaudeId) {
        log.info("Gerente {} vinculando profissional da saúde {} do posto de saúde {}", jwt.getSubject(), profissionalSaudeId, postoSaudeId);
        postoSaudeController.vincularProfissionalSaudePostoSaude(Long.valueOf(jwt.getSubject()), profissionalSaudeId, postoSaudeId);
        log.info("Gerente {} vinculou profissional da saúde {} do posto de saúde {}", jwt.getSubject(), profissionalSaudeId, postoSaudeId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Remove um profissional da saúde de um posto de saúde",
            description = "Endpoint restrito a usuários GERENTE",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Profissional da saúde removido com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Gerente não pertence ao posto de saúde informado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'GERENTE'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Posto de saúde não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PutMapping("/profissionais-saude/remover/{profissionalSaudeId}/{postoSaudeId}")
    public ResponseEntity<Void> removerProfissionalSaudePostoSaude(@AuthenticationPrincipal Jwt jwt,
                                                @PathVariable("profissionalSaudeId") Long profissionalSaudeId,
                                                @PathVariable("postoSaudeId") Long postoSaudeId) {
        log.info("Gerente {} removendo profissional da saúde {} do posto de saúde {}", jwt.getSubject(), profissionalSaudeId, postoSaudeId);
        postoSaudeController.removerProfissionalSaudePostoSaude(Long.valueOf(jwt.getSubject()), profissionalSaudeId, postoSaudeId);
        log.info("Gerente {} removeu profissional da saúde {} do posto de saúde {}", jwt.getSubject(), profissionalSaudeId, postoSaudeId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
