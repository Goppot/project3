package com.amr.project.inserttestdata;

import com.amr.project.inserttestdata.repository.*;
import com.amr.project.model.entity.*;
import com.amr.project.model.enums.Gender;
import com.amr.project.model.enums.Status;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.*;

@Component
@AllArgsConstructor
public class DataInserting {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ShopRepository shopRepository;
    @Autowired
    private final CountryRepository countryRepository;
    @Autowired
    private final CityRepository cityRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final ItemRepository itemRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final CartItemRepository cartItemRepository;
    @Autowired
    private final FavoriteRepository favoriteRepository;
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final ChatRepository chatRepository;
    @Autowired
    private final DiscountRepository discountRepository;
    @Autowired
    private final FeedbackRepository feedbackRepository;
    @Autowired
    private final AnswerFromModeratorRepository answerFromModeratorRepository;
    @Autowired
    private final CouponRepository couponRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @PostConstruct
    public void init() throws IOException {
//---------------------------------------------------------------Roles
        Role roleAdmin = Role.builder().name("ADMIN").build();
        Role roleModerator = Role.builder().name("MODERATOR").build();
        Role roleUser = Role.builder().name("USER").build();
        roleRepository.save(roleAdmin);
        roleRepository.save(roleModerator);
        roleRepository.save(roleUser);
//---------------------------------------------------------------Cities
        City moscow = City.builder().name("Moscow").build();
        City petersburg = City.builder().name("Saint-Petersburg").build();
        City novosibirsk = City.builder().name("Novosibirsk").build();

        City kyiv = City.builder().name("Kyiv").build();
        City kharkiv = City.builder().name("Kharkiv").build();
        City odessa = City.builder().name("Odessa").build();

        City minsk = City.builder().name("Minsk").build();
        City gomel = City.builder().name("Gomel").build();
        City vitebsk = City.builder().name("Vitebsk").build();

        cityRepository.save(moscow);
        cityRepository.save(petersburg);
        cityRepository.save(novosibirsk);

        cityRepository.save(kyiv);
        cityRepository.save(kharkiv);
        cityRepository.save(odessa);

        cityRepository.save(minsk);
        cityRepository.save(gomel);
        cityRepository.save(vitebsk);
//---------------------------------------------------------------Countries
        Country russia = Country.builder().name("Russia").cities(List.of(moscow, petersburg, novosibirsk)).build();
        Country ukraine = Country.builder().name("Ukraine").cities(List.of(kyiv, kharkiv, odessa)).build();
        Country belorus = Country.builder().name("Belorus").cities(List.of(minsk, gomel, vitebsk)).build();

        countryRepository.save(russia);
        countryRepository.save(ukraine);
        countryRepository.save(belorus);
//---------------------------------------------------------------Addresses
        Address address1 = Address.builder()
                .cityIndex("123456")
                .country(countryRepository.findByName("Russia"))
                .city(cityRepository.findByName("Moscow"))
                .street("user1_street")
                .house("user1_house")
                .build();
        Address address2 = Address.builder()
                .cityIndex("123456")
                .country(countryRepository.findByName("Russia"))
                .city(cityRepository.findByName("Saint-petersburg"))
                .street("user2_street")
                .house("user2_house")
                .build();
        Address address3 = Address.builder()
                .cityIndex("123456")
                .country(countryRepository.findByName("Russia"))
                .city(cityRepository.findByName("Novosibirsk"))
                .street("user3_street")
                .house("user3_house")
                .build();
        Address address4 = Address.builder()
                .cityIndex("123456")
                .country(countryRepository.findByName("Ukraine"))
                .city(cityRepository.findByName("Kyiv"))
                .street("user4_street")
                .house("user4_house")
                .build();
        Address address5 = Address.builder()
                .cityIndex("123456")
                .country(countryRepository.findByName("Ukraine"))
                .city(cityRepository.findByName("Kharkiv"))
                .street("user5_street")
                .house("user5_street")
                .build();
        Address address6 = Address.builder()
                .cityIndex("123456")
                .country(countryRepository.findByName("Ukraine"))
                .city(cityRepository.findByName("Odessa"))
                .street("user6_street")
                .house("user6_street")
                .build();
//---------------------------------------------------------------Images
        File admin_image =  ResourceUtils.getFile("classpath:static/images/users/admin_image.jpg");
        File moderator_image =  ResourceUtils.getFile("classpath:static/images/users/moderator_image.jpg");

        File user1_image =  ResourceUtils.getFile("classpath:static/images/users/user1_image.jpg");
        File user2_image =  ResourceUtils.getFile("classpath:static/images/users/user2_image.jpg");
        File user3_image =  ResourceUtils.getFile("classpath:static/images/users/user3_image.jpg");
        File user4_image =  ResourceUtils.getFile("classpath:static/images/users/user4_image.jpg");
        File user5_image =  ResourceUtils.getFile("classpath:static/images/users/user5_image.jpg");
        File user6_image =  ResourceUtils.getFile("classpath:static/images/users/user6_image.jpg");

        File shop1_image =  ResourceUtils.getFile("classpath:static/images/shops/shop1_image.jpg");
        File shop2_image =  ResourceUtils.getFile("classpath:static/images/shops/shop2_image.jpg");
        File shop3_image =  ResourceUtils.getFile("classpath:static/images/shops/shop3_image.jpg");

        File item1_image1 =  ResourceUtils.getFile("classpath:static/images/items/item1_image1.jpg");
        File item1_image2 =  ResourceUtils.getFile("classpath:static/images/items/item1_image2.jpg");
        File item1_image3 =  ResourceUtils.getFile("classpath:static/images/items/item1_image3.jpg");

        File item2_image1 =  ResourceUtils.getFile("classpath:static/images/items/item2_image1.jpg");
        File item2_image2 =  ResourceUtils.getFile("classpath:static/images/items/item2_image2.jpg");
        File item2_image3 =  ResourceUtils.getFile("classpath:static/images/items/item2_image3.jpg");

        File item3_image1 =  ResourceUtils.getFile("classpath:static/images/items/item3_image1.jpg");
        File item3_image2 =  ResourceUtils.getFile("classpath:static/images/items/item3_image2.jpg");
        File item3_image3 =  ResourceUtils.getFile("classpath:static/images/items/item3_image3.jpg");

        File item4_image1 =  ResourceUtils.getFile("classpath:static/images/items/item4_image1.jpg");
        File item4_image2 =  ResourceUtils.getFile("classpath:static/images/items/item4_image2.jpg");
        File item4_image3 =  ResourceUtils.getFile("classpath:static/images/items/item4_image3.jpg");

        File item5_image1 =  ResourceUtils.getFile("classpath:static/images/items/item5_image1.jpg");
        File item5_image2 =  ResourceUtils.getFile("classpath:static/images/items/item5_image2.jpg");
        File item5_image3 =  ResourceUtils.getFile("classpath:static/images/items/item5_image3.jpg");

        File item6_image1 =  ResourceUtils.getFile("classpath:static/images/items/item6_image1.jpg");
        File item6_image2 =  ResourceUtils.getFile("classpath:static/images/items/item6_image2.jpg");
        File item6_image3 =  ResourceUtils.getFile("classpath:static/images/items/item6_image3.jpg");

        byte[] array_admin_image = Files.readAllBytes(admin_image.toPath());
        byte[] array_moderator_image = Files.readAllBytes(moderator_image.toPath());
        byte[] array_user1_image = Files.readAllBytes(user1_image.toPath());
        byte[] array_user2_image = Files.readAllBytes(user2_image.toPath());
        byte[] array_user3_image = Files.readAllBytes(user3_image.toPath());
        byte[] array_user4_image = Files.readAllBytes(user4_image.toPath());
        byte[] array_user5_image = Files.readAllBytes(user5_image.toPath());
        byte[] array_user6_image = Files.readAllBytes(user6_image.toPath());

        byte[] array_shop1_image = Files.readAllBytes(shop1_image.toPath());
        byte[] array_shop2_image = Files.readAllBytes(shop2_image.toPath());
        byte[] array_shop3_image = Files.readAllBytes(shop3_image.toPath());

        byte[] array_item1_image1 = Files.readAllBytes(item1_image1.toPath());
        byte[] array_item1_image2 = Files.readAllBytes(item1_image2.toPath());
        byte[] array_item1_image3 = Files.readAllBytes(item1_image3.toPath());

        byte[] array_item2_image1 = Files.readAllBytes(item2_image1.toPath());
        byte[] array_item2_image2 = Files.readAllBytes(item2_image2.toPath());
        byte[] array_item2_image3 = Files.readAllBytes(item2_image3.toPath());

        byte[] array_item3_image1 = Files.readAllBytes(item3_image1.toPath());
        byte[] array_item3_image2 = Files.readAllBytes(item3_image2.toPath());
        byte[] array_item3_image3 = Files.readAllBytes(item3_image3.toPath());

        byte[] array_item4_image1 = Files.readAllBytes(item4_image1.toPath());
        byte[] array_item4_image2 = Files.readAllBytes(item4_image2.toPath());
        byte[] array_item4_image3 = Files.readAllBytes(item4_image3.toPath());

        byte[] array_item5_image1 = Files.readAllBytes(item5_image1.toPath());
        byte[] array_item5_image2 = Files.readAllBytes(item5_image2.toPath());
        byte[] array_item5_image3 = Files.readAllBytes(item5_image3.toPath());

        byte[] array_item6_image1 = Files.readAllBytes(item6_image1.toPath());
        byte[] array_item6_image2 = Files.readAllBytes(item6_image2.toPath());
        byte[] array_item6_image3 = Files.readAllBytes(item6_image3.toPath());

        Image adminImage = Image.builder().picture(array_admin_image).isMain(true).build();
        Image moderatorImage = Image.builder().picture(array_moderator_image).isMain(true).build();
        Image user1Image = Image.builder().picture(array_user1_image).isMain(true).build();
        Image user2Image = Image.builder().picture(array_user2_image).isMain(true).build();
        Image user3Image = Image.builder().picture(array_user3_image).isMain(true).build();
        Image user4Image = Image.builder().picture(array_user4_image).isMain(true).build();
        Image user5Image = Image.builder().picture(array_user5_image).isMain(true).build();
        Image user6Image = Image.builder().picture(array_user6_image).isMain(true).build();

        Image shop1Image = Image.builder().picture(array_shop1_image).isMain(true).build();
        Image shop2Image = Image.builder().picture(array_shop2_image).isMain(true).build();
        Image shop3Image = Image.builder().picture(array_shop3_image).isMain(true).build();

        Image item1Image1 = Image.builder().picture(array_item1_image1).isMain(true).build();
        Image item1Image2 = Image.builder().picture(array_item1_image2).isMain(false).build();
        Image item1Image3 = Image.builder().picture(array_item1_image3).isMain(false).build();

        Image item2Image1 = Image.builder().picture(array_item2_image1).isMain(true).build();
        Image item2Image2 = Image.builder().picture(array_item2_image2).isMain(false).build();
        Image item2Image3 = Image.builder().picture(array_item2_image3).isMain(false).build();

        Image item3Image1 = Image.builder().picture(array_item3_image1).isMain(true).build();
        Image item3Image2 = Image.builder().picture(array_item3_image2).isMain(false).build();
        Image item3Image3 = Image.builder().picture(array_item3_image3).isMain(false).build();

        Image item4Image1 = Image.builder().picture(array_item4_image1).isMain(true).build();
        Image item4Image2 = Image.builder().picture(array_item4_image2).isMain(false).build();
        Image item4Image3 = Image.builder().picture(array_item4_image3).isMain(false).build();

        Image item5Image1 = Image.builder().picture(array_item5_image1).isMain(true).build();
        Image item5Image2 = Image.builder().picture(array_item5_image2).isMain(false).build();
        Image item5Image3 = Image.builder().picture(array_item5_image3).isMain(false).build();

        Image item6Image1 = Image.builder().picture(array_item6_image1).isMain(true).build();
        Image item6Image2 = Image.builder().picture(array_item6_image2).isMain(false).build();
        Image item6Image3 = Image.builder().picture(array_item6_image3).isMain(false).build();
//---------------------------------------------------------------Admin + Moderator
        User admin1 = User.builder()
                .email("admin1@mail")
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .activate(true)
                .activationCode("admin1_activation_code")
                .phone("admin1_phone")
                .firstName("admin1_firstname")
                .lastName("admin1_lastname")
                .address(null)
                .age(0)
                .roles(Set.of(roleRepository.findByName("ADMIN")))
                .gender(Gender.UNKNOWN)
                .birthday(null)
                .images(adminImage)
                .coupons(null)
                .orders(null)
                .reviews(null)
                .shops(null)
                .favorite(null)
                .discounts(null)
                .build();
        User moderator1 = User.builder()
                .email("moderator1@mail")
                .username("moderator1_username")
                .password(passwordEncoder.encode("password"))
                .activate(true)
                .activationCode("moderator1_activation_code")
                .phone("moderator1_phone")
                .firstName("moderator1_firstname")
                .lastName("moderator1_lastname")
                .address(null)
                .age(0)
                .roles(Set.of(roleRepository.findByName("MODERATOR")))
                .gender(Gender.UNKNOWN)
                .birthday(null)
                .images(moderatorImage)
                .coupons(null)
                .orders(null)
                .reviews(null)
                .shops(null)
                .favorite(null)
                .discounts(null)
                .build();
        userRepository.save(admin1);
        userRepository.save(moderator1);
//---------------------------------------------------------------Users
        User user1 = User.builder()
                .email("user1@mail")
                .username("user1_username")
                .password(passwordEncoder.encode("password"))
                .activate(true)
                .activationCode("user1_activation_code")
                .phone("user1_phone")
                .firstName("user1_firstname")
                .lastName("user1_lastname")
                .address(address1)
                .age(30)
                .roles(Set.of(roleRepository.findByName("USER")))
                .gender(Gender.MALE)
                .birthday(new GregorianCalendar(1991, 1, 1))
                .images(user1Image)
                .coupons(null)
                .orders(null)
                .reviews(null)
                .shops(null)
                .favorite(null)
                .discounts(null)
                .build();
        User user2 = User.builder()
                .email("user2@mail")
                .username("user2_username")
                .password(passwordEncoder.encode("password"))
                .activate(true)
                .activationCode("user2_activation_code")
                .phone("user2_phone")
                .firstName("user2_firstname")
                .lastName("user2_lastname")
                .address(address2)
                .age(29)
                .roles(Set.of(roleRepository.findByName("USER")))
                .gender(Gender.MALE)
                .birthday(new GregorianCalendar(1992, 2, 2))
                .images(user2Image)
                .coupons(null)
                .orders(null)
                .reviews(null)
                .shops(null)
                .favorite(null)
                .discounts(null)
                .build();
        User user3 = User.builder()
                .email("user3@mail")
                .username("user3_username")
                .password(passwordEncoder.encode("password"))
                .activate(true)
                .activationCode("user3_activation_code")
                .phone("user3_phone")
                .firstName("user3_firstname")
                .lastName("user3_lastname")
                .address(address3)
                .age(28)
                .roles(Set.of(roleRepository.findByName("USER")))
                .gender(Gender.MALE)
                .birthday(new GregorianCalendar(1993, 3, 3))
                .images(user3Image)
                .coupons(null)
                .orders(null)
                .reviews(null)
                .shops(null)
                .favorite(null)
                .discounts(null)
                .build();
        User user4 = User.builder()
                .email("user4@mail")
                .username("user4_username")
                .password(passwordEncoder.encode("password"))
                .activate(true)
                .activationCode("user4_activation_code")
                .phone("user4_phone")
                .firstName("user4_firstname")
                .lastName("user4_lastname")
                .address(address4)
                .age(27)
                .roles(Set.of(roleRepository.findByName("USER")))
                .gender(Gender.FEMALE)
                .birthday(new GregorianCalendar(1994, 4, 4))
                .images(user4Image)
                .coupons(null)
                .orders(null)
                .reviews(null)
                .shops(null)
                .favorite(null)
                .discounts(null)
                .build();
        User user5 = User.builder()
                .email("user5@mail")
                .username("user5_username")
                .password(passwordEncoder.encode("password"))
                .activate(true)
                .activationCode("user5_activation_code")
                .phone("user5_phone")
                .firstName("user5_firstname")
                .lastName("user5_lastname")
                .address(address5)
                .age(26)
                .roles(Set.of(roleRepository.findByName("USER")))
                .gender(Gender.FEMALE)
                .birthday(new GregorianCalendar(1995, 5, 5))
                .images(user5Image)
                .coupons(null)
                .orders(null)
                .reviews(null)
                .shops(null)
                .favorite(null)
                .discounts(null)
                .build();
        User user6 = User.builder()
                .email("user6@mail")
                .username("user6_username")
                .password(passwordEncoder.encode("password"))
                .activate(true)
                .activationCode("user6_activation_code")
                .phone("user6_phone")
                .firstName("user6_firstname")
                .lastName("user6_lastname")
                .address(address6)
                .age(25)
                .roles(Set.of(roleRepository.findByName("USER")))
                .gender(Gender.FEMALE)
                .birthday(new GregorianCalendar(1996, 6, 6))
                .images(user6Image)
                .coupons(null)
                .orders(null)
                .reviews(null)
                .shops(null)
                .favorite(null)
                .discounts(null)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);
//---------------------------------------------------------------Shops
        Shop shop1 = Shop.builder()
                .name("shop1")
                .email("shop1@mail")
                .phone("shop1_phone")
                .description("shop1_description")
                .location(russia)
                .items(null)
                .reviews(null)
                .logo(List.of(shop1Image))
                .count(0)
                .rating(4.5)
                .user(userRepository.findByEmail("user1@mail"))
                .discounts(null)
                .isModerated(false)
                .isModerateAccept(false)
                .moderatedRejectReason(null)
                .activity(0)
                .file(null)
                .isPretendentToBeDeleted(false)
                .build();
        Shop shop2 = Shop.builder()
                .name("shop2")
                .email("shop2@mail")
                .phone("shop2_phone")
                .description("shop2_description")
                .location(ukraine)
                .items(null)
                .reviews(null)
                .logo(List.of(shop2Image))
                .count(0)
                .rating(0)
                .user(userRepository.findByEmail("user2@mail"))
                .discounts(null)
                .isModerated(false)
                .isModerateAccept(false)
                .moderatedRejectReason(null)
                .activity(0)
                .file(null)
                .isPretendentToBeDeleted(false)
                .build();
        User user3_shop = userRepository.findByEmail("user3@mail");
        Shop shop3 = Shop.builder()
                .name("shop3")
                .email("shop3@mail")
                .phone("shop3_phone")
                .description("shop3_description")
                .location(belorus)
                .items(null)
                .reviews(null)
                .logo(List.of(shop3Image))
                .count(0)
                .rating(0)
                .user(userRepository.findByEmail("user3@mail"))
                .discounts(null)
                .isModerated(false)
                .isModerateAccept(false)
                .moderatedRejectReason(null)
                .activity(0)
                .file(null)
                .isPretendentToBeDeleted(false)
                .build();
        shopRepository.save(shop1);
        shopRepository.save(shop2);
        shopRepository.save(shop3);

        Shop user3_shop_managed = shopRepository.findByUserId(user3_shop.getId());
        user3_shop.setShops(List.of(user3_shop_managed));
        userRepository.save(user3_shop); // создалась связь в таблицу user_shop


//---------------------------------------------------------------Categories
        Category category1 = Category.builder().name("category1").build();
        Category category2 = Category.builder().name("category2").build();
        Category category3 = Category.builder().name("category3").build();
        Category category4 = Category.builder().name("category4").build();
        Category category5 = Category.builder().name("category5").build();
        Category category6 = Category.builder().name("category6").build();

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);
        categoryRepository.save(category5);
        categoryRepository.save(category6);
//---------------------------------------------------------------Items
        Item item1 = Item.builder()
                .name("item1")
                .price(new BigDecimal("111111"))
                .categories(List.of(categoryRepository.findByName("category1")))
                .images(List.of(item1Image1, item1Image2, item1Image3))
                .reviews(null)
                .count(100)
                .rating(4.0)
                .description("item1_description")
                .discount(0)
                .shop(shop1)
                .isModerated(false)
                .isModerateAccept(false)
                .moderatedRejectReason(null)
                .isPretendentToBeDeleted(false)
                .build();
        Item item2 = Item.builder()
                .name("item2")
                .price(new BigDecimal("222222"))
                .categories(List.of(categoryRepository.findByName("category1"), categoryRepository.findByName("category2")))
                .images(List.of(item2Image1, item2Image2, item2Image3))
                .reviews(null)
                .count(200)
                .rating(5.0)
                .description("item2_description")
                .discount(0)
                .shop(shop1)
                .isModerated(false)
                .isModerateAccept(false)
                .moderatedRejectReason(null)
                .isPretendentToBeDeleted(false)
                .build();
        Item item3 = Item.builder()
                .name("item3")
                .price(new BigDecimal("333333"))
                .categories(List.of(categoryRepository.findByName("category1"), categoryRepository.findByName("category2"), categoryRepository.findByName("category3")))
                .images(List.of(item3Image1, item3Image2, item3Image3))
                .reviews(null)
                .count(300)
                .rating(0D)
                .description("item3_description")
                .discount(0)
                .shop(shop1)
                .isModerated(false)
                .isModerateAccept(false)
                .moderatedRejectReason(null)
                .isPretendentToBeDeleted(false)
                .build();
        Item item4 = Item.builder()
                .name("item4")
                .price(new BigDecimal("444444"))
                .categories(List.of(categoryRepository.findByName("category4")))
                .images(List.of(item4Image1, item4Image2, item4Image3))
                .reviews(null)
                .count(400)
                .rating(0D)
                .description("item4_description")
                .discount(0)
                .shop(shop2)
                .isModerated(false)
                .isModerateAccept(false)
                .moderatedRejectReason(null)
                .isPretendentToBeDeleted(false)
                .build();
        Item item5 = Item.builder()
                .name("item5")
                .price(new BigDecimal("555555"))
                .categories(List.of(categoryRepository.findByName("category4"), categoryRepository.findByName("category5")))
                .images(List.of(item5Image1, item5Image2, item5Image3))
                .reviews(null)
                .count(500)
                .rating(0D)
                .description("item5_description")
                .discount(0)
                .shop(shop2)
                .isModerated(false)
                .isModerateAccept(false)
                .moderatedRejectReason(null)
                .isPretendentToBeDeleted(false)
                .build();
        Item item6 = Item.builder()
                .name("item6")
                .price(new BigDecimal("666666"))
                .categories(List.of(categoryRepository.findByName("category4"), categoryRepository.findByName("category5"), categoryRepository.findByName("category6")))
                .images(List.of(item6Image1, item6Image2, item6Image3))
                .reviews(null)
                .count(600)
                .rating(0D)
                .description("item6_description")
                .discount(0)
                .shop(shop2)
                .isModerated(false)
                .isModerateAccept(false)
                .moderatedRejectReason(null)
                .isPretendentToBeDeleted(false)
                .build();

        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        itemRepository.save(item4);
        itemRepository.save(item5);
        itemRepository.save(item6);
//---------------------------------------------------------------Shop_Items
        shop1.setItems(List.of(item1, item2, item3));
        shop2.setItems(List.of(item4, item5, item6));

        shopRepository.save(shop1);
        shopRepository.save(shop2);
//---------------------------------------------------------------CartItem
        User user4_cartItem = userRepository.findByEmail("user4@mail");
        User user5_cartItem = userRepository.findByEmail("user5@mail");

        Item item1_cart_item1 = itemRepository.findByName("item1");
        Item item2_cart_item2 = itemRepository.findByName("item2");
        Item item3_cart_item3 = itemRepository.findByName("item3");
        Item item4_cart_item4 = itemRepository.findByName("item4");
        Item item5_cart_item5 = itemRepository.findByName("item5");
        Item item6_cart_item6 = itemRepository.findByName("item6");

        CartItem cartItem1 = CartItem.builder()
                .item(item1_cart_item1)
                .shop(item1_cart_item1.getShop())
                .user(user4_cartItem)
                .quantity(2)
                .build();
        CartItem cartItem2 = CartItem.builder()
                .item(item2_cart_item2)
                .shop(item2_cart_item2.getShop())
                .user(user4_cartItem)
                .quantity(2)
                .build();
        CartItem cartItem3 = CartItem.builder()
                .item(item3_cart_item3)
                .shop(item3_cart_item3.getShop())
                .user(user4_cartItem)
                .quantity(2)
                .build();

        CartItem cartItem4 = CartItem.builder()
                .item(item4_cart_item4)
                .shop(item4_cart_item4.getShop())
                .user(user5_cartItem)
                .quantity(2)
                .build();
        CartItem cartItem5 = CartItem.builder()
                .item(item5_cart_item5)
                .shop(item5_cart_item5.getShop())
                .user(user5_cartItem)
                .quantity(2)
                .build();
        CartItem cartItem6 = CartItem.builder()
                .item(item6_cart_item6)
                .shop(item6_cart_item6.getShop())
                .user(user5_cartItem)
                .quantity(2)
                .build();

        cartItemRepository.save(cartItem1);
        cartItemRepository.save(cartItem2);
        cartItemRepository.save(cartItem3);

        cartItemRepository.save(cartItem4);
        cartItemRepository.save(cartItem5);
        cartItemRepository.save(cartItem6);

        user4_cartItem.setCartItems(List.of(cartItem1, cartItem2, cartItem3));
        user5_cartItem.setCartItems(List.of(cartItem4, cartItem5, cartItem6));

        userRepository.save(user4_cartItem);
        userRepository.save(user5_cartItem);

        cartItem1 = cartItemRepository.findById(1L).orElse(null);
        cartItem2 = cartItemRepository.findById(2L).orElse(null);
        cartItem3 = cartItemRepository.findById(3L).orElse(null);
        cartItem4 = cartItemRepository.findById(4L).orElse(null);
        cartItem5 = cartItemRepository.findById(5L).orElse(null);
        cartItem6 = cartItemRepository.findById(6L).orElse(null);

        item1_cart_item1.setCartItems(List.of(cartItem1));
        item2_cart_item2.setCartItems(List.of(cartItem2));
        item3_cart_item3.setCartItems(List.of(cartItem3));
        item4_cart_item4.setCartItems(List.of(cartItem4));
        item5_cart_item5.setCartItems(List.of(cartItem5));
        item6_cart_item6.setCartItems(List.of(cartItem6));

        itemRepository.save(item1_cart_item1);
        itemRepository.save(item2_cart_item2);
        itemRepository.save(item3_cart_item3);
        itemRepository.save(item4_cart_item4);
        itemRepository.save(item5_cart_item5);
        itemRepository.save(item6_cart_item6);

//---------------------------------------------------------------Favourite
        User user4_favorite = userRepository.findByEmail("user4@mail");
        User user5_favorite = userRepository.findByEmail("user5@mail");
        User user6_favorite = userRepository.findByEmail("user6@mail");

        Favorite favorite_user4 = Favorite.builder()
                .items(List.of(item1))
                .shops(List.of(shop1))
                .user(user4_favorite)
                .build();
        Favorite favorite_user5 = Favorite.builder()
                .items(List.of(item1, item4))
                .shops(List.of(shop1, shop2))
                .user(user5_favorite)
                .build();
        Favorite favorite_user6 = Favorite.builder()
                .items(List.of(item1, item2, item6))
                .shops(List.of(shop1, shop2))
                .user(user6_favorite)
                .build();

        favoriteRepository.save(favorite_user4);
        favoriteRepository.save(favorite_user5);
        favoriteRepository.save(favorite_user6);

        user4_favorite.setFavorite(favoriteRepository.findByUserId(user4_favorite.getId()));
        userRepository.save(user4_favorite);

        user5_favorite.setFavorite(favoriteRepository.findByUserId(user5_favorite.getId()));
        userRepository.save(user5_favorite);

        user6_favorite.setFavorite(favoriteRepository.findByUserId(user6_favorite.getId()));
        userRepository.save(user6_favorite);
//---------------------------------------------------------------Orders
        User user4_order = userRepository.findByEmail("user4@mail");
        Item user4_order_item1 = itemRepository.findByName("item1");
        Item user4_order_item2 = itemRepository.findByName("item2");

        User user5_order = userRepository.findByEmail("user5@mail");
        Item user5_order_item1 = itemRepository.findByName("item3");
        Item user5_order_item2 = itemRepository.findByName("item4");

        User user6_order = userRepository.findByEmail("user6@mail");
        Item user6_order_item1 = itemRepository.findByName("item5");
        Item user6_order_item2 = itemRepository.findByName("item6");

        Order order_user4 = Order.builder()
                .items(List.of(user4_order_item1, user4_order_item2))
                .date(GregorianCalendar.getInstance())
                .status(Status.START)
                .address(user4_order.getAddress())
                .total(itemRepository.findByName("item1").getPrice().add(itemRepository.findByName("item2").getPrice()))
                .user(user4_order)
                .buyerName(user4_order.getUsername())
                .buyerPhone(user4_order.getPhone())
                .build();
        Order order_user5 = Order.builder()
                .items(List.of(user5_order_item1, user5_order_item2))
                .date(GregorianCalendar.getInstance())
                .status(Status.START)
                .address(user5_order.getAddress())
                .total(user5_order_item1.getPrice().add(user5_order_item2.getPrice()))
                .user(user5_order)
                .buyerName(user5_order.getUsername())
                .buyerPhone(user5_order.getPhone())
                .build();
        Order order_user6 = Order.builder()
                .items(List.of(user6_order_item1, user6_order_item2))
                .date(GregorianCalendar.getInstance())
                .status(Status.START)
                .address(user6_order.getAddress())
                .total(user6_order_item1.getPrice().add(user6_order_item2.getPrice()))
                .user(user6_order)
                .buyerName(user6_order.getUsername())
                .buyerPhone(user6_order.getPhone())
                .build();

        user4_order.setOrders(List.of(order_user4));
        user5_order.setOrders(List.of(order_user5));
        user6_order.setOrders(List.of(order_user6));

        userRepository.save(user4_order);
        userRepository.save(user5_order);
        userRepository.save(user6_order);
//---------------------------------------------------------------Reviews
        //Ревью user4 на товар item1
        User user4_review = userRepository.findByEmail("user4@mail");
        Item item_review1_user4 = itemRepository.findByName("item1");
        Shop shop_review1_user4 = shopRepository.findByName("shop1");
        Review review1_user4_item = Review.builder()
                .dignity("dignity_review_user4")
                .flaw("flaw_review_user4")
                .text("text_review_user4")
                .date(GregorianCalendar.getInstance().getTime())
                .rating(4)
                .user(null) //detached entity passed to persist: com.amr.project.model.entity.User
                .item(null)
                .shop(null)
                .isModerated(false)
                .isModerateAccept(false)
                .moderatedRejectReason(null)
                .logo(null)
                .file(null)
                .build();
        reviewRepository.save(review1_user4_item);

        review1_user4_item.setItem(item_review1_user4);
        review1_user4_item.setShop(shop_review1_user4);
        review1_user4_item.setUser(user4_review);
        reviewRepository.save(review1_user4_item); //создалась связь в колонке user_id (таблица review) и item_review

        Review review1_user4_item_managed = reviewRepository.findByUserId(user4_review.getId());
        user4_review.setReviews(List.of(review1_user4_item_managed));
        userRepository.save(user4_review); // создалась связь в таблицу user_review

        //Ревью user5 на магазин shop1
        User user5_review = userRepository.findByEmail("user5@mail");
        Item item_review1_user5 = itemRepository.findByName("item2");
        Shop shop_review1_user5 = shopRepository.findByName("shop1");
        Review review1_user5_shop = Review.builder()
                .dignity("dignity_review_user5")
                .flaw("flaw_review_user5")
                .text("text_review_user5")
                .date(GregorianCalendar.getInstance().getTime())
                .rating(5)
                .user(null) //detached entity passed to persist: com.amr.project.model.entity.User
                .item(null)
                .shop(null)
                .logo(null)
                .isModerated(false)
                .isModerateAccept(false)
                .moderatedRejectReason(null)
                .file(null)
                .build();
        reviewRepository.save(review1_user5_shop);
        review1_user5_shop.setItem(item_review1_user5);
        review1_user5_shop.setShop(shop_review1_user5);
        review1_user5_shop.setUser(user5_review);
        reviewRepository.save(review1_user5_shop); //создалась связь в колонке user_id (таблица review) и shop_review

        Review review1_user5_shop_managed = reviewRepository.findByUserId(user5_review.getId());
        user5_review.setReviews(List.of(review1_user5_shop_managed));
        userRepository.save(user5_review); // создалась связь в таблицу user_review

        shop_review1_user5.setReviews(List.of(review1_user5_shop_managed));
        shopRepository.save(shop_review1_user5); //создалась связь в таблицу shop_review
//---------------------------------------------------------------Chats
        //Chat1
        User chat1_member1 = userRepository.findByEmail("moderator1@mail");
        User chat1_member2 = userRepository.findByEmail("user1@mail");

        Chat chat1 = Chat.builder().members(List.of(chat1_member1, chat1_member2)).build();

        Message message1_chat1 = Message.builder()
                .chat(chat1)
                .from(chat1_member1)
                .to(chat1_member2)
                .textMessage("message1")
                .viewed(false)
                .build();
        Message message2_chat1 = Message.builder()
                .chat(chat1)
                .from(chat1_member2)
                .to(chat1_member1)
                .textMessage("message2")
                .viewed(false)
                .build();
        Message message3_chat1 = Message.builder()
                .chat(chat1)
                .from(chat1_member2)
                .to(chat1_member1)
                .textMessage("message3")
                .viewed(false)
                .build();

        chat1.setMessages(List.of(message1_chat1, message2_chat1, message3_chat1));
        chatRepository.save(chat1);

        //Chat2
        User chat2_member1 = userRepository.findByEmail("user1@mail");
        User chat2_member2 = userRepository.findByEmail("user4@mail");

        Chat chat2 = Chat.builder().members(List.of(chat2_member1, chat2_member2)).build();

        Message message1_chat2 = Message.builder()
                .chat(chat2)
                .from(chat2_member1)
                .to(chat2_member2)
                .textMessage("message1")
                .viewed(false)
                .build();
        Message message2_chat2 = Message.builder()
                .chat(chat2)
                .from(chat2_member2)
                .to(chat2_member1)
                .textMessage("message2")
                .viewed(false)
                .build();
        Message message3_chat2 = Message.builder()
                .chat(chat2)
                .from(chat2_member2)
                .to(chat2_member1)
                .textMessage("message3")
                .viewed(false)
                .build();

        chat2.setMessages(List.of(message1_chat2, message2_chat2, message3_chat2));
        chatRepository.save(chat2);
//---------------------------------------------------------------Discounts
        //Discount2
        Shop shop1_discount1 = shopRepository.findByName("shop1");
        User user4_discount1 = userRepository.findByEmail("user4@mail");

        Discount discount1 = Discount.builder()
                .minOrder(1000)
                .percentage(10)
                .fixedDiscount(100)
                .shop(shop1_discount1)
                .user(user4_discount1)
                .build();
        discountRepository.save(discount1);

        user4_discount1.setDiscounts(List.of(discount1));
        userRepository.save(user4_discount1);

        shop1_discount1.setDiscounts(List.of(discount1));
        shopRepository.save(shop1_discount1);

        //Discount2
        Shop shop2_discount1 = shopRepository.findByName("shop2");
        User user5_discount1 = userRepository.findByEmail("user5@mail");

        Discount discount2 = Discount.builder()
                .minOrder(2000)
                .percentage(20)
                .fixedDiscount(200)
                .shop(shop2_discount1)
                .user(user5_discount1)
                .build();
        discountRepository.save(discount2);

        user5_discount1.setDiscounts(List.of(discount2));
        userRepository.save(user5_discount1);

        shop2_discount1.setDiscounts(List.of(discount2));
        shopRepository.save(shop2_discount1);
//---------------------------------------------------------------Coupons
        User user4_coupons = userRepository.findByEmail("user4@mail");
        User user5_coupons = userRepository.findByEmail("user5@mail");
        User user6_coupons = userRepository.findByEmail("user6@mail");

        Coupon coupon1 = new Coupon();
        Coupon coupon2 = new Coupon();
        Coupon coupon3 = new Coupon();

        couponRepository.save(coupon1);
        couponRepository.save(coupon2);
        couponRepository.save(coupon3);

        user4_coupons.setCoupons(List.of(coupon1));
        user5_coupons.setCoupons(List.of(coupon1, coupon2));
        user6_coupons.setCoupons(List.of(coupon1, coupon2, coupon3));

        userRepository.save(user4_coupons);
        userRepository.save(user5_coupons);
        userRepository.save(user6_coupons);
//---------------------------------------------------------------Feedback
        Feedback feedback1 = Feedback.builder()
                .full_text("full_text_feedback1")
                .reason("reason_feedback1")
                .build();
        Feedback feedback2 = Feedback.builder()
                .full_text("full_text_feedback1")
                .reason("reason_feedback2")
                .build();
        feedbackRepository.save(feedback1);
        feedbackRepository.save(feedback2);
//---------------------------------------------------------------Answer from moderator
        AnswerFromModerator answerFromModerator1 = AnswerFromModerator.builder()
                .answer_from_moderator("answer_from_moderator_1")
                .build();
        AnswerFromModerator answerFromModerator2 = AnswerFromModerator
                .builder().answer_from_moderator("answer_from_moderator_2")
                .build();

        answerFromModeratorRepository.save(answerFromModerator1);
        answerFromModeratorRepository.save(answerFromModerator2);
    }
}
