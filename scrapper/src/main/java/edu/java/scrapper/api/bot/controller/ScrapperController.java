package edu.java.scrapper.api.bot.controller;

import edu.java.scrapper.api.bot.dto.request.LinkDto;
import edu.java.scrapper.api.bot.dto.response.ErrorResponseDto;
import edu.java.scrapper.api.bot.dto.response.IsOpenChatDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(SCRAPPER)
@CrossOrigin(origins = "*")
@Tag(
    name = "api.scrapper.controller.tag.name",
    description = "api.scrapper.controller.tag.description"
)
public class ScrapperController {

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
    public ResponseEntity<?> saveLink(@Valid @RequestBody LinkDto linkDto) {
        return ResponseEntity.ok().build(); // TODO: connect with db later
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
        return ResponseEntity.ok().build(); // TODO: connect with db later
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
    public ResponseEntity<IsOpenChatDto> checkChat(
        @Parameter(
            description = "api.scrapper.endpoint.check-chat.param.id.description",
            example = "123",
            required = true
        )
        @PathVariable
        Long id
    ) {

        IsOpenChatDto isOpenChatDto = IsOpenChatDto.builder()
            .isOpen(true) // TODO: connect with db later
            .build();

        return ResponseEntity.ok(isOpenChatDto);
    }

}
