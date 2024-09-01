-------------------------------------------------- main.cpp --------------------------------------------------
#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <map>
#include <string>
#include <dirent.h>
#include <sys/stat.h>
#include <algorithm>

using namespace std;

vector<string> getFilesInDirectory(const string& directory, const string& extension) {
    vector<string> files;
    DIR* dirp = opendir(directory.c_str());
    struct dirent* dp;
    struct stat fileStat;

    while ((dp = readdir(dirp)) != NULL) {
        string filePath = directory + "/" + dp->d_name;
        if (stat(filePath.c_str(), &fileStat) == 0 && S_ISREG(fileStat.st_mode)) {
            string filename = dp->d_name;
            if (filename.size() >= extension.size() && filename.substr(filename.size() - extension.size()) == extension) {
                files.push_back(filePath);
            }
        }
    }
    closedir(dirp);
    return files;
}

set<string> getWordsFromFile(const string& filePath) {
    ifstream file(filePath);
    set<string> words;
    string word;

    while (file >> word) {
        words.insert(word);
    }
    return words;
}

void writeWordsToFile(const string& filePath, const set<string>& words) {
    ofstream file(filePath);
    for (const string& word : words) {
        file << word << endl;
    }
}

int main(int argc, char* argv[]) {
    if (argc != 2) {
        cerr << "Usage: " << argv[0] << " <directory>" << endl;
        return 1;
    }

    string directory = argv[1];
    vector<string> txtFiles = getFilesInDirectory(directory, ".txt");

    set<string> allWords;
    set<string> sharedWords;
    bool firstFile = true;

    for (const string& file : txtFiles) {
        set<string> words = getWordsFromFile(file);
        allWords.insert(words.begin(), words.end());
        if (firstFile) {
            sharedWords = words;
            firstFile = false;
        }
        else {
            set<string> temp;
            set_intersection(sharedWords.begin(), sharedWords.end(), words.begin(), words.end(), inserter(temp, temp.begin()));
            sharedWords = temp;
        }
    }

    writeWordsToFile("all.txt", allWords);
    writeWordsToFile("shared.txt", sharedWords);

    return 0;
}
