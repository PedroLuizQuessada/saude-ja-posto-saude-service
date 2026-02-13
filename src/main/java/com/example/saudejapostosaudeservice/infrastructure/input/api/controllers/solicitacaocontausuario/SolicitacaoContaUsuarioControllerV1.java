package com.example.saudejapostosaudeservice.infrastructure.input.api.controllers.solicitacaocontausuario;

import com.example.saudejapostosaudeservice.controllers.SolicitacaoVinculoPacientePostoSaudeController;
import com.example.saudejapostosaudeservice.datasources.NotificacaoDataSource;
import com.example.saudejapostosaudeservice.datasources.PostoSaudeDataSource;
import com.example.saudejapostosaudeservice.datasources.SolicitacaoVinculoPacientePostoSaudeDataSource;
import com.example.saudejapostosaudeservice.datasources.UsuarioDataSource;
import dtos.responses.SolicitacaoVinculoPacientePostoSaudeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(path = "/api/v1/solicitacoes-vinculo-paciente-posto-saude")
@Tag(name = "Solicitações de Vínculo de Pacientes com Postos de Saúde API V1", description = "Versão 1 do controlador referente a solicitações de vínculo de pacientes com postos de saúde")
@Profile("api")
public class SolicitacaoContaUsuarioControllerV1 {

    private final SolicitacaoVinculoPacientePostoSaudeController solicitacaoVinculoPacientePostoSaudeController;

    public SolicitacaoContaUsuarioControllerV1(PostoSaudeDataSource postoSaudeDataSource, SolicitacaoVinculoPacientePostoSaudeDataSource solicitacaoVinculoPacientePostoSaudeDataSource, UsuarioDataSource usuarioDataSource, NotificacaoDataSource notificacaoDataSource) {
        this.solicitacaoVinculoPacientePostoSaudeController = new SolicitacaoVinculoPacientePostoSaudeController(postoSaudeDataSource, solicitacaoVinculoPacientePostoSaudeDataSource, usuarioDataSource, notificacaoDataSource);
    }

    @Operation(summary = "Solicita o vínculo de um paciente com um posto de saúde",
            description = "Endpoint restrito a usuários PACIENTE",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Solicitação criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SolicitacaoVinculoPacientePostoSaudeResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Valores inválidos para os atributos do usuário paciente a ser vinculado ao posto de saúde",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",
                    description = "Credenciais de acesso inválidas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403",
                    description = "Usuário autenticado não é 'PACIENTE'",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404",
                    description = "Posto de saúde não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PostMapping("{postoSaudeId}")
    public ResponseEntity<SolicitacaoVinculoPacientePostoSaudeResponse> solicitarVinculoPacientePostoSaude(@AuthenticationPrincipal Jwt jwt, @PathVariable("postoSaudeId") Long postoSaudeId) {
        log.info("Criando solicitação de vínculo do paciente {} no posto de saúde {}", jwt.getSubject(), postoSaudeId);
        SolicitacaoVinculoPacientePostoSaudeResponse solicitacaoVinculoPacientePostoSaudeResponse =
                solicitacaoVinculoPacientePostoSaudeController.solicitarVinculoPacientePostoSaude(Long.valueOf(jwt.getSubject()), postoSaudeId);
        log.info("Criada solicitação de vínculo do paciente {} no posto de saúde {}", jwt.getSubject(), postoSaudeId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(solicitacaoVinculoPacientePostoSaudeResponse);
    }

    @Operation(summary = "Consome solicitação de vínculo do paciente em posto de saúde",
            description = "Endpoint restrito a usuários AGENTE_COMUNITARIO",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Solicitação consumida e usuário paciente vinculado com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Código inválido ou solicitação já consumida ou vencida",
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
                    description = "Solicitação não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))),
    })
    @PutMapping("/{solicitacaoVinculoPacientePostoSaudeId}")
    public ResponseEntity<Void> consumirSolicitacaoVinculoPacientePostoSaude(@AuthenticationPrincipal Jwt jwt,
                                                                             @PathVariable("solicitacaoVinculoPacientePostoSaudeId") Long solicitacaoVinculoPacientePostoSaudeId) {
        log.info("Agente comunitário {} consumindo solicitação {} de vínculo do paciente em posto de saúde", jwt.getSubject(), solicitacaoVinculoPacientePostoSaudeId);
        solicitacaoVinculoPacientePostoSaudeController.consumirSolicitacaoVinculoPacientePostoSaude(Long.valueOf(jwt.getSubject()), solicitacaoVinculoPacientePostoSaudeId);
        log.info("Agente comunitário {} consumindo solicitação {} de vínculo do paciente em posto de saúde", jwt.getSubject(), solicitacaoVinculoPacientePostoSaudeId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
