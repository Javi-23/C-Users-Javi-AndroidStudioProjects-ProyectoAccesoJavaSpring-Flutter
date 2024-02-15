import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: Scaffold(
        appBar: AppBar(
          title: Text('Consultar Perfil De Un Usuario'),
        ),
        body: UserFetchScreen(),
      ),
    );
  }
}

class UserFetchScreen extends StatefulWidget {
  @override
  _UserFetchScreenState createState() => _UserFetchScreenState();
}

class _UserFetchScreenState extends State<UserFetchScreen> {
  TextEditingController usernameController = TextEditingController();
  List<Map<String, dynamic>> userData = [];

  Future<void> fetchUserData(String username) async {
    try {
      final response = await http.get(
          Uri.parse('http://localhost:8080/api/v1/users/public/$username'));

      if (response.statusCode == 200) {
        var jsonData = jsonDecode(response.body);
        setState(() {
          userData = jsonData is List ? List.from(jsonData) : [jsonData];
        });
      } else {
        setState(() {
          userData = [];
        });
      }
    } catch (e) {
      setState(() {
        userData = [];
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          TextField(
            controller: usernameController,
            decoration: InputDecoration(
              labelText: 'Username',
              border: OutlineInputBorder(),
              contentPadding: EdgeInsets.all(12),
            ),
          ),
          SizedBox(height: 16),
          ElevatedButton(
            onPressed: () {
              String username = usernameController.text.trim();
              if (username.isNotEmpty) {
                fetchUserData(username);
              }
            },
            child: Text('Buscar Perfil'),
          ),
          SizedBox(height: 16),
          if (userData.isNotEmpty)
            SingleChildScrollView(
              scrollDirection: Axis.horizontal,
              child: DataTable(
                columns: userData[0]
                    .keys
                    .map((key) => DataColumn(label: Text(key)))
                    .toList(),
                rows: userData
                    .map(
                      (data) => DataRow(
                        cells: data.values
                            .map((value) => DataCell(Text(value.toString())))
                            .toList(),
                      ),
                    )
                    .toList(),
              ),
            ),
          if (userData.isEmpty) Text('Perfil no identificado.'),
        ],
      ),
    );
  }
}
