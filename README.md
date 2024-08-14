
# Otel Yönetim Sistemi

Otel Yönetim Sistemi projesi; otel rezervasyonları, misafir yönetimi, oda ve fiyatlandırma işlemlerini kolaylaştıran bir sistemdir.
## Kullanılan Teknolojiler

Java 17, Spring Boot, PostgreSQL, Postman, Hibernate, Maven, Intellij IDEA
## Özellikler

- Misafir Yönetimi
- Oda Yönetimi
- Fiyatlandırma Yönetimi
- Rezervasyon Yönetimi

### Misafir Bilgileri

- **id**: Misafirin kimlik numarası.
- **name**: Misafirin adı.
- **surname**: Misafirin soyadı.
- **tcPassportNumber**: Misafirin TC kimlik numarası veya pasaport numarası.
- **email**: Misafirin e-postası.
- **phone**: Misafirin telefon numarası.
- **country**: Misafirin uyruğu.

### Oda Bilgileri

- **id**: Odanın kimlik numarası.
- **roomNumber**: Odanın numarası.
- **description**: Odanın tipi (örneğin, standart, süit).
- **prices**: Oda için geçerli olan fiyatlandırma listesi.

### Fiyatlandırma Bilgileri

- **id**: Fiyatlandırmanın kimlik numarası.
- **name**: Fiyatlandırmanın adı.
- **price**: Fiyat miktarı.
- **startDate**: Fiyatın geçerli olmaya başladığı tarih.
- **endDate**: Fiyatın geçerliliğinin sona erdiği tarih.
- **rooms**: Bu fiyatın geçerli olduğu odaların listesi.

### Rezervasyon Bilgileri

- **id**: Rezervasyonun kimlik numarası.
- **checkinDate**: Rezervasyonun başlangıç tarihi.
- **checkoutDate**: Rezervasyonun bitiş tarihi.
- **guests**: Rezervasyona dahil olan misafirlerin listesi.
- **totalPrice**: Rezervasyonun toplam maliyeti.
- **room**: Rezervasyonun yapıldığı odanın listesi.
- **reservationStatus**: Rezervasyonun durumu.

Rezervasyon Durumları:
  - `PRE_BOOKING`: Ön rezervasyon
  - `CONFIRMED_BOOKING`: Kesin rezervasyon
  - `CURRENTLY_STAYING`: Şu an otelde
  - `CHECKED_OUT`: Çıkış yapıldı

## Bilgisayarınızda Çalıştırın

### Projeyi klonlayın

```bash
  git clone https://github.com/sinandgnn/Hotel-Management-System.git
```

### Proje dizinine gidin

```bash
  cd Hotel-Management-System
```

### Gerekli paketleri yükleyin

```bash
  mvn install
```

### Veritabanı Yapılandırması


Uygulamanızın çalışabilmesi için veritabanı bağlantı ayarlarını ".env" dosyasında yapılandırmanız gerekmektedir.

#### .env Dosyası İçeriği

    'DB_URL': Veritabanı bağlantı URL'si.
    'DB_USERNAME': Veritabanı kullanıcı adı.
    'DB_PASSWORD': Veritabanı şifresi.

Örnek .env dosyası:
   
    DB_URL=jdbc:postgresql://localhost:5432/hotelmanagement
    DB_USERNAME=postgres
    DB_PASSWORD=123456


### Sunucuyu çalıştırın

```bash
  mvn spring-boot:run
```

  
## API Kullanımı


### Misafir API'ları

#### Misafir Oluştur

```http
POST /api/guest/create
```

| Parametre | Tip     | Açıklama                |
| :-------- | :------- | :------------------------- |
| `body` | `JSON` | Misafir bilgilerini içeren JSON nesnesi. |

#### Misafiri Getir

```http
GET /api/guest/get
```

| Parametre | Tip     | Açıklama                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | Misafirin id'si. |

#### Tüm Misafirleri Getir

```http
GET /api/guest/getAll
```

#### Misafiri Sil

```http
DELETE /api/guest/delete
```
| Parametre | Tip     | Açıklama                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | Silinecek misafirin id'si. |

#### Misafiri Güncelle

```http
PUT /api/guest/update
```

| Parametre | Tip     | Açıklama                |
| :-------- | :------- | :------------------------- |
| `id`      | `Long` | Güncellenecek misafirin id'si. |
| `body` | `JSON` | Güncellenecek bilgileri içeren JSON nesnesi. |



## Oda API'ları

#### Oda Oluştur

```http
POST /api/room/create
```

| Parametre | Tip     | Açıklama                |
| :-------- | :------- | :------------------------- |
| `body` | `JSON` | Oda bilgilerini içeren JSON nesnesi. |

#### Odayı Getir

```http
GET /api/room/get
```

| Parametre | Tip     | Açıklama                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | Odanın id'si. |

#### Tüm Odaları Getir

```http
GET /api/room/getAll
```

#### Odayı Sil

```http
DELETE /api/room/delete
```
| Parametre | Tip     | Açıklama                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | Silinecek odanın id'si. |

#### Odayı Güncelle

```http
PUT /api/room/update
```

| Parametre | Tip     | Açıklama                |
| :-------- | :------- | :------------------------- |
| `id`      | `Long` | Güncellenecek odanın id'si. |
| `body` | `JSON` | Güncellenecek bilgileri içeren JSON nesnesi. |

#### Odaya Fiyatlandırma Ekle

```http
POST /api/room/addPrice
```

| Parametre | Tip     | Açıklama                |
| :-------- | :------- | :------------------------- |
| `roomId`      | `Long` | Fiyatlandırma eklenecek odanın id'si. |
| `priceId` | `Long` | Eklenecek olan fiyatlandırmanın id'si. |

#### Odadan Fiyatlandırmayı Sil

```http
DELETE /api/room/deletePrice
```

| Parametre | Tip     | Açıklama                |
| :-------- | :------- | :------------------------- |
| `roomId`      | `Long` | Fiyatlandırmanın silineceği odanın id'si. |
| `priceId` | `Long` | Silinecek olan fiyatlandırmanın id'si. |



## Fiyatlandırma API'ları

#### Fiyatlandırma Oluştur

```http
POST /api/price/create
```

| Parametre | Tip     | Açıklama                |
| :-------- | :------- | :------------------------- |
| `body` | `JSON` | Fiyatlandırma bilgilerini içeren JSON nesnesi. |

#### Fiyatlandırmayı Getir

```http
GET /api/price/get
```

| Parametre | Tip     | Açıklama                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | Fiyatlandırmanın id'si. |

#### Tüm Fiyatlandırmaları Getir

```http
GET /api/price/getAll
```

#### Fiyatlandırmayı Sil

```http
DELETE /api/price/delete
```
| Parametre | Tip     | Açıklama                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | Silinecek fiyatlandırmanın id'si. |

#### Fiyatlandırmayı Güncelle

```http
PUT /api/price/update
```

| Parametre | Tip     | Açıklama                |
| :-------- | :------- | :------------------------- |
| `id`      | `Long` | Güncellenecek fiyatlandırmanın id'si. |
| `body` | `JSON` | Güncellenecek bilgileri içeren JSON nesnesi. |



## Rezervasyon API'ları

#### Rezervasyon Oluştur

```http
POST /api/reservation/create
```

| Parametre | Tip     | Açıklama                |
| :-------- | :------- | :------------------------- |
| `body` | `JSON` | Rezervasyon bilgilerini içeren JSON nesnesi. |

#### Rezervasyonu Getir

```http
GET /api/reservation/get
```

| Parametre | Tip     | Açıklama                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | Rezervasyonun id'si. |

#### Tüm Rezervasyonları Getir

```http
GET /api/reservation/getAll
```

#### Rezervasyonu Sil

```http
DELETE /api/reservation/delete
```
| Parametre | Tip     | Açıklama                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | Silinecek rezervasyonun id'si. |

#### Rezervasyonu Güncelle

```http
PUT /api/reservation/update
```

| Parametre | Tip     | Açıklama                |
| :-------- | :------- | :------------------------- |
| `id`      | `Long` | Güncellenecek rezervasyonun id'si. |
| `body` | `JSON` | Güncellenecek bilgileri içeren JSON nesnesi. |

#### Rezervasyonu Güncelle

```http
PUT /api/reservation/updateStatus
```

| Parametre | Tip     | Açıklama                |
| :-------- | :------- | :------------------------- |
| `id`      | `Long` | Güncellenecek rezervasyonun id'si. |
| `status` | `String` | Rezervasyon durumu. |

#### Rezervasyona Misafir Ekle

```http
POST /api/reservation/addGuest
```

| Parametre | Tip     | Açıklama                |
| :-------- | :------- | :------------------------- |
| `reservationId`      | `Long` | Rezervasyonun id'si. |
| `guestId`      | `Long` | Rezervasyona eklenecek olan misafirin id'si. |

#### Rezervasyondan Misafir Çıkar

```http
DELETE /api/reservation/removeGuest
```

| Parametre | Tip     | Açıklama                |
| :-------- | :------- | :------------------------- |
| `reservationId`      | `Long` | Rezervasyonun id'si. |
| `guestId`      | `Long` | Rezervasyondan çıkartılacak olan misafirin id'si. |

## UML Diagram

![Diagram](https://i.hizliresim.com/798axoz.jpg)

  
## Destek

Destek için sinandgn00@gmail.com adresine e-posta gönderebilirsiniz.
