package edu.java.scrapper.api.bot.controller;

import edu.java.scrapper.api.bot.dto.request.LinkDto;
import edu.java.scrapper.api.bot.dto.request.UntrackLinkDto;
import edu.java.scrapper.api.bot.dto.response.ErrorResponseDto;
import edu.java.scrapper.api.bot.dto.response.IsActiveChatDto;
import edu.java.scrapper.core.service.ChatService;
import edu.java.scrapper.core.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static edu.java.scrapper.api.bot.ApiPath.CHAT;
import static edu.java.scrapper.api.bot.ApiPath.ID;
import static edu.java.scrapper.api.bot.ApiPath.LINK;
import static edu.java.scrapper.api.bot.ApiPath.SCRAPPER;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(SCRAPPER)
@CrossOrigin(origins = "*")
@Tag(
    name = "api.scrapper.controller.tag.name",
    description = "api.scrapper.controller.tag.description"
)
public class ScrapperController {

    private final ChatService chatService;

    private final LinkService linkService;

    @GetMapping(LINK + ID)
    @Operation(summary = "Получить все отслеживаемые ссылки пользователя.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "api.response.ok"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Не удалось найти ссылки для данного чата.",
            content = {
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponseDto.class)
                )
            }),
        @ApiResponse(
            responseCode = "500",
            description = "api.response.internal-server-error",
            content = {
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponseDto.class)
                )
            })
    })
    public ResponseEntity<?> findLinks(
        @Parameter(
            description = "api.scrapper.endpoint.open-chat.param.id.description",
            example = "123",
            required = true
        )
        @PathVariable
        Long id
    ) {
        var links = linkService.getAllForChat(id);
        return ResponseEntity.ok(links);
    }

    @PostMapping(LINK)
    @Operation(summary = "api.scrapper.endpoint.link.summary")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "api.response.ok"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "api.scrapper.endpoint.link.response.bad-request",
            content = {
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponseDto.class)
                )
            }),
        @ApiResponse(
            responseCode = "500",
            description = "api.response.internal-server-error",
            content = {
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponseDto.class)
                )
            })
    })
    public ResponseEntity<?> trackLink(@Valid @RequestBody LinkDto linkDto) {
        linkService.track(linkDto);
        log.trace("[CONTROLLER] :: Received a linkDto.");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(LINK)
    @Operation(summary = "Перестать отслеживать ссылку.") // TODO: move to properties
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "api.response.ok"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "api.scrapper.endpoint.link.response.bad-request",
            content = {
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponseDto.class)
                )
            }),
        @ApiResponse(
            responseCode = "500",
            description = "api.response.internal-server-error",
            content = {
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponseDto.class)
                )
            })
    })
    public ResponseEntity<?> untrackLink(@Valid @RequestBody UntrackLinkDto linkDto) {
        linkService.untrack(linkDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(CHAT + ID)
    @Operation(summary = "api.scrapper.endpoint.open-chat.summary")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "api.response.ok"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "api.response.internal-server-error",
            content = {
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponseDto.class)
                )
            })
    })
    public ResponseEntity<?> openChat(
        @Parameter(
            description = "api.scrapper.endpoint.open-chat.param.id.description",
            example = "123",
            required = true
        )
        @PathVariable
        Long id
    ) {
        log.trace("[CONTROLLER] :: Opening chat {} ...", id);
        chatService.activate(id);
        log.trace("[CONTROLLER] :: Opening chat {} ... Done!", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(CHAT + ID)
    @Operation(summary = "api.scrapper.endpoint.check-chat.summary")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "api.response.ok"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "api.response.internal-server-error",
            content = {
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponseDto.class)
                )
            })
    })
    public ResponseEntity<IsActiveChatDto> checkChat(
        @Parameter(
            description = "api.scrapper.endpoint.check-chat.param.id.description",
            example = "123",
            required = true
        )
        @PathVariable
        Long id
    ) {
        var isActiveChatDto = chatService.checkIfActivated(id);
        return ResponseEntity.ok(isActiveChatDto);
    }

    @DeleteMapping(CHAT + ID)
    @Operation(summary = "Удаление чата в телеграм из бота.") // TODO: move msg to properties
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "api.response.ok"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "api.response.internal-server-error",
            content = {
                @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponseDto.class)
                )
            })
    })
    public ResponseEntity<Void> deleteChat(
        @Parameter(
            description = "Айди чата в телеграм.", // TODO: move msg to properties
            example = "123",
            required = true
        )
        @PathVariable
        Long id
    ) {
        chatService.delete(id);
        return ResponseEntity.ok().build();
    }

}
