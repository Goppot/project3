package com.amr.project.webapp.rest_controller;

import com.amr.project.model.entity.*;
import com.amr.project.service.abstracts.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/api")
@Api(tags = {"API для работы с панелью администратора"})
public class AdminControllerRest {

    private CategoryService categoryService;
    private ShopService shopService;
    private UserService userService;
    private ItemService itemService;
    private CityService cityService;
    private CountryService countryService;
    private AddressService addressService;

    @Autowired
    public AdminControllerRest(CategoryService categoryService, ShopService shopService, UserService userService,
                               ItemService itemService, CityService cityService, CountryService countryService,
                               AddressService addressService) {
        this.categoryService = categoryService;
        this.shopService = shopService;
        this.userService = userService;
        this.itemService = itemService;
        this.cityService = cityService;
        this.countryService = countryService;
        this.addressService = addressService;
    }

    @ApiOperation(value = "Отправляет один магазин на фронт")
    @GetMapping("/shops/{id}")
    public Shop getShopById(@PathVariable("id") Long id) { return shopService.getByKey(id);}

    @ApiOperation(value = "Отправляет одного пользователя на фронт")
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") Long id) { return userService.getByKey(id);}

    @ApiOperation(value = "Отправляет один айтем на фронт")
    @GetMapping("/items/{id}")
    public Item getItemById(@PathVariable("id") Long id) { return itemService.getByKey(id);}

    @ApiOperation(value = "Отправляет одну страну на фронт")
    @GetMapping("/countries/{id}")
    public Country getCountryById(@PathVariable("id") Long id) { return countryService.getByKey(id);}

    @ApiOperation(value = "Отправляет один город на фронт")
    @GetMapping("/cities/{id}")
    public City getCityById(@PathVariable("id") Long id) { return cityService.getByKey(id);}

    @ApiOperation(value = "Отправляет один адрес на фронт")
    @GetMapping("/addresses/{id}")
    public Address getAddressById(@PathVariable("id") Long id) { return addressService.getByKey(id);}

    @ApiOperation(value = "Отправляет одну категорию на фронт")
    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable("id") Long id) { return categoryService.getByKey(id);}

    @ApiOperation(value = "Отправляет все магазины на фронт")
    @GetMapping("/shops")
    public List<Shop> getAllShops() { return shopService.getAll(); }

    @ApiOperation(value = "Отправляет всех пользователей на фронт")
    @GetMapping("/users")
    public List<User> getAllUsers() { return userService.getAll(); }

    @ApiOperation(value = "Отправляет все айтемы на фронт")
    @GetMapping("/items")
    public List<Item> getAllItem() { return itemService.getAll(); }

    @ApiOperation(value = "Отправляет все страны на фронт")
    @GetMapping("/countries")
    public List<Country> getAllCountries() { return countryService.getAll(); }

    @ApiOperation(value = "Отправляет все города на фронт")
    @GetMapping("/cities")
    public List<City> getAllCities() { return cityService.getAll(); }

    @ApiOperation(value = "Отправляет все адреса на фронт")
    @GetMapping("/addresses")
    public List<Address> getAllAddresses() { return addressService.getAll(); }

    @ApiOperation(value = "Отправляет все категории на фронт")
    @GetMapping("/categories")
    public List<Category> getAllCategories() { return categoryService.getAll(); }

    @ApiOperation(value = "Добавляет новый магазин в БД")
    @PostMapping("/shops")
    public void addShop(@RequestBody Shop shop) { shopService.persist(shop); }

    @ApiOperation(value = "Добавляет нового пользователя в БД")
    @PostMapping("/users")
    public void addUser(@RequestBody User user) { userService.persist(user); }

    @ApiOperation(value = "Добавляет новый айтем в БД")
    @PostMapping("/items")
    public void addItem(@RequestBody Item item) { itemService.persist(item); }

    @ApiOperation(value = "Добавляет новую страну в БД")
    @PostMapping("/countries")
    public void addCountry(@RequestBody Country country) { countryService.persist(country); }

    @ApiOperation(value = "Добавляет новый город в БД")
    @PostMapping("/cities")
    public void addCity(@RequestBody City city) { cityService.persist(city); }

    @ApiOperation(value = "Добавляет новый адрес в БД")
    @PostMapping("/addresses")
    public void addAddress(@RequestBody Address address) { addressService.persist(address); }

    @ApiOperation(value = "Добавляет новую категорию в БД")
    @PostMapping("/categories")
    public void addCategory(@RequestBody Category category) { categoryService.persist(category); }

    @ApiOperation(value = "Обновляет магазин в БД")
    @PutMapping("/shops")
    public void updateShop(@RequestBody Shop shop) { shopService.update(shop); }

    @ApiOperation(value = "Обновляет пользователя в БД")
    @PutMapping("/users")
    public void updateUser(@RequestBody User user) { userService.update(user); }

    @ApiOperation(value = "Обновляет айтем в БД")
    @PutMapping("/items")
    public void updateItem(@RequestBody Item item) { itemService.update(item); }

    @ApiOperation(value = "Обновляет страну в БД")
    @PutMapping("/countries")
    public void updateCountry(@RequestBody Country country) { countryService.update(country); }

    @ApiOperation(value = "Обновляет город в БД")
    @PutMapping("/cities")
    public void updateCity(@RequestBody City city) { cityService.update(city); }

    @ApiOperation(value = "Обновляет адрес в БД")
    @PutMapping("/addresses")
    public void updateAddress(@RequestBody Address address) { addressService.update(address); }

    @ApiOperation(value = "Обновляет категорию в БД")
    @PutMapping("/categories")
    public void updateCategory(@RequestBody Category category) { categoryService.update(category); }

    @ApiOperation(value = "Удаляет магазин из БД")
    @DeleteMapping("/shops/{id}")
    public void deleteShopById(@PathVariable("id") Long id) { shopService.delete(shopService.getByKey(id)); }

    @ApiOperation(value = "Удаляет пользователя из БД")
    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable("id") Long id) { userService.delete(userService.getByKey(id)); }

    @ApiOperation(value = "Удаляет айтем из БД")
    @DeleteMapping("/items/{id}")
    public void deleteItemById(@PathVariable("id") Long id) { itemService.delete(itemService.getByKey(id)); }

    @ApiOperation(value = "Удаляет адрес из БД")
    @DeleteMapping("/addresses/{id}")
    public void deleteAddressesById(@PathVariable("id") Long id) { addressService.delete(addressService.getByKey(id)); }

    @ApiOperation(value = "Удаляет страну из БД")
    @DeleteMapping("/countries/{id}")
    public void deleteCountriesById(@PathVariable("id") Long id) { countryService.delete(countryService.getByKey(id)); }

    @ApiOperation(value = "Удаляет магазин из БД")
    @DeleteMapping("/cities/{id}")
    public void deleteCitiesById(@PathVariable("id") Long id) { cityService.delete(cityService.getByKey(id)); }

    @ApiOperation(value = "Удаляет категорию из БД")
    @DeleteMapping(value = "/categories/{id}")
    public void deleteCategoriesById(@PathVariable("id") Long id) {
        categoryService.deleteByKeyCascadeEnable(id);
          }
}
