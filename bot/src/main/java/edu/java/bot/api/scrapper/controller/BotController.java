package edu.java.bot.api.scrapper.controller;

import com.pengrad.telegrambot.model.request.ParseMode;
import edu.java.bot.api.scrapper.dto.request.UpdateDto;
import edu.java.bot.api.scrapper.dto.response.ErrorResponseDto;
import edu.java.bot.core.telegram.service.BotService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static edu.java.bot.api.scrapper.ApiPath.BOT;
import static edu.java.bot.api.scrapper.ApiPath.UPDATES;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(BOT)
@Log4j2
@CrossOrigin(origins = "*")
@Tag(
    name = "api.bot.controller.tag.name",
    description = "api.bot.controller.tag.description"
)
public class BotController {

    private final BotService botService;

    @PutMapping(UPDATES)
    @Operation(summary = "api.bot.endpoint.catch-updates.summary")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "api.response.ok"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "api.bot.endpoint.catch-updates.response.bad-request",
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
    public ResponseEntity<?> catchUpdates(@Valid @RequestBody UpdateDto update) {
        botService.sendUpdate(update, ParseMode.HTML);
        return ResponseEntity.ok().build();
    }

}
