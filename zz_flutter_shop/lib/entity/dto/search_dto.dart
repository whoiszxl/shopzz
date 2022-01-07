
class SearchDTO {
  SearchDTO({
    this.keyword,
    this.data,
  });

  String keyword;
  List<SearchItem> data;

  Map<String, dynamic> toJson() => <String, dynamic>{
    'keyword': keyword,
    'data': data,
  };
}

class SearchItem {
  SearchItem({
    this.word,
    this.url,
    this.type,
    this.price,
    this.star,
    this.zonename,
    this.districtname,
  });

  String word;
  String type;
  String price;
  String star;
  String zonename;
  String districtname;
  String url;


  Map<String, dynamic> toJson() => <String, dynamic>{
    'word': word,
    'type': type,
    'price': price,
    'star': star,
    'zonename': zonename,
    'districtname': districtname,
    'url': url,
  };

}