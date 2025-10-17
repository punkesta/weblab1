## Аналіз домену

### a. Піддомени системи

1. **Інентар**  
    Відповідає за зберігання, організацію і керування даними ігрових предметів користувача.  
    Основні функції: огляд, продаж, редагування окремих ігрових предметів.
    
2. **Аутентифікація і юзери**  
    Забезпечує вхід користувачів у систему і розділення їхніх прав і повноважень.  
    Основні ролі: **Адміністратор** (управління користувачами/видача банів) та **Користувач** (керування своїм ігровим прогресом).
    
3. **Профіль**  
    Забезпечує організацію і керування особистими даними користувача.
    Основні функції: редагування облікових даних, зміна аватару, перегляд мета-інформації акаунту користувача.
    
### b. Ключові сутності у кожному піддомені

| Піддомен                        | Ключові сутності             | Опис                                                                                                                                  |
| ------------------------------- | ---------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| **Інентар**                     | `BaseItem`, `InventoryItem`  | Ігрові предмети користувача зі змінним станом та їх батьківські прототипи (ідентифікатор, тип, назва, рідкість, колекція, ціна).      |
| **Аутентифікація і юзери**      | `CustomUserDetails`          | Облікові дані користувачів і їх повноваження в межах системи.                                                                         |
| **Профіль**                     | `Profile`                    | Зовнішнє представлення особистих, публічних і мета-даних користувача.                                                                 |

### Реалізація моделей

```java
public class BaseItem {
    public final String id;
    public final Type type;
    public final String name;
    public final Weapon weapon;
    public final Rarity rarity;
    public final Collection collection;
    public final double price;
    public final String iconPath;
    
    public BaseItem(
        String id,
        Type type,
        String name,
        Weapon weapon,
        Rarity rarity,
        Collection collection,
        double price,
        String iconPath
    ) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.weapon = weapon;
        this.rarity = rarity;
        this.collection = collection;
        this.price = price;
        this.iconPath = iconPath;
    }
}
}
```

```java
public class InventoryItem {
    private final int id;
    private final int ownerId;
    private final BaseItem baseItem;
    private boolean isNew;
    private boolean isStatTrack;
    private boolean isEquippedCt;
    private boolean isEquippedT;
    
    public InventoryItem(
        int id,
        int ownerId,
        BaseItem baseItem,
        boolean isNew,
        boolean isStatTrack,
        boolean isEquippedCt,
        boolean isEquippedT
    ) {
        this.id = id;
        this.ownerId = ownerId;
        this.baseItem = baseItem;
        this.isNew = isNew;
        this.isStatTrack = isStatTrack;
        this.isEquippedCt = isEquippedCt;
        this.isEquippedT = isEquippedT;
    }
    
    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public BaseItem getBaseItem() {
        return baseItem;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean isStatTrack() {
        return isStatTrack;
    }

    public void setStatTrack(boolean isStatTrack) {
        if (baseItem.type != Type.GUN) {
            return;
        }
        this.isStatTrack = isStatTrack;
    }

    public boolean isEquippedCt() {
        return isEquippedCt;
    }

    public void setEquippedCt(boolean isEuippedCt) {
        this.isEquippedCt = isEquippedCt;
    }

    public boolean isEquippedT() {
        return isEquippedT;
    }

    public void setEquippedT(boolean isEquippedT) {
        this.isEquippedT = isEquippedT;
    }
}
```

```java
@Data
public class CustomUserDetails implements UserDetails {    
    private int userId;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> roles;
    private boolean isBanned;

    public CustomUserDetails(
        int userId,
        String username,
        String password,
        Collection<? extends GrantedAuthority> roles,
        boolean isBanned
    ) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.isBanned = isBanned;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBanned;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {      
        return roles;
    }
}
```

```java
public class Profile {
    private final int id;
    private String idLabel;
    private String name;
    private double goldBalance;
    private double coinBalance;
    private String avatarPath;
    private final Date registrationDate;

    public Profile(
        int id,
        String idLabel,
        String name,
        double goldBalance,
        double coinBalance,
        String avatarPath,
        Date registrationDate
    ) {
        this.id = id;
        this.idLabel = idLabel;
        this.name = name;
        this.goldBalance = goldBalance;
        this.coinBalance = coinBalance;
        this.avatarPath = avatarPath;
        this.registrationDate = registrationDate;
    }
    
    public int getId() {
        return id;
    }

    public String getIdLabel() {
        return idLabel;
    }

    public String getName() {
        return name;
    }

    public double getGoldBalance() {
        return goldBalance;
    }

    public double getCoinBalance() {
        return coinBalance;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setIdLabel(String idLabel) {
        this.idLabel = idLabel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGoldBalance(double goldBalance) {
        this.goldBalance = goldBalance;
    }

    public void setCoinBalance(double coinBalance) {
        this.coinBalance = coinBalance;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
