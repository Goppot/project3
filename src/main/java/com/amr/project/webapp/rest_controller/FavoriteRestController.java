package com.amr.project.webapp.rest_controller;
import com.amr.project.converter.FavoriteMapper;
import com.amr.project.model.dto.FavoriteDto;
import com.amr.project.model.entity.Favorite;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.FavoriteService;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@Api(tags = {"API для работы с вкладкой Избранное "})
@RequestMapping("/favorite/")
public class FavoriteRestController {

    private final FavoriteService favoriteService;
    private final UserService userService;
    private final FavoriteMapper favoriteMapper;
    private final ItemService itemService;


    @GetMapping("/getFavoriteByUser")
    public ResponseEntity<FavoriteDto> getFavorite(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated() || (authentication instanceof AnonymousAuthenticationToken)) {
            throw new AccessDeniedException("Вам нужно авторизоваться для доступа к избранному");
        }
        if (principal != null) {
            Favorite favorite = favoriteService.findByUser(userService.findByUsername(principal.getName()));
            if (favorite != null) {
                return ResponseEntity.ok(favoriteMapper.favoriteToDto(favorite));
            }
        }
            return ResponseEntity.badRequest().build();
    }

    @Transactional
    @PatchMapping("/items/add/{id}")
    public ResponseEntity<Void> addItemToFavorites(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated() || (authentication instanceof AnonymousAuthenticationToken)) {
            throw new AccessDeniedException("Вам нужно авторизоваться для того чтобы добавить в избранное");
        }
        User user = userService.findByUsername(authentication.getName());
        Favorite favorite;
        if (user.getFavorite() == null) {
            favorite = new Favorite();
            favorite.setUser(user);
            favoriteService.persist(favorite);
            user.setFavorite(favorite);
        } else {
            favorite = user.getFavorite();
        }
        if(favorite.getItems() == null) {
            List<Item> items = new ArrayList<>();
            favorite.setItems(items);
        }
        Item item = itemService.getByKey(id);
        if (!favorite.getItems().contains(item)) {
            favorite.getItems().add(item);
        }
        favoriteService.update(favorite);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Transactional
    @DeleteMapping("/items/delete/{id}")
    public ResponseEntity<Void> deleteFavoriteItem(@PathVariable Long id) {
        Item item = itemService.getByKey(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated() || (authentication instanceof AnonymousAuthenticationToken)) {
            throw new AccessDeniedException("Вам нужно авторизоваться для доступа к избранному");
        }
        User user = userService.findByUsername(authentication.getName());
        Favorite favorite = favoriteService.findByUser(user);
        if (favorite != null) {
            favorite.getItems().remove(item);
        }
        return ResponseEntity.ok().body(null);
    }
}
