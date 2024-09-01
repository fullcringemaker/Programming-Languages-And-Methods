-------------------------------------------------- main.cpp --------------------------------------------------
#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <set>
#include <sys/types.h>
#include <dirent.h>
#include <sys/stat.h>
#include <unistd.h>

std::set<std::string> findCursivePhrases(const std::string& filename) {
    std::ifstream file(filename);
    std::set<std::string> phrases;
    std::string line;
    while (std::getline(file, line)) {
        size_t start = 0;
        while ((start = line.find('*', start)) != std::string::npos) {
            size_t end = line.find('*', start + 1);
            if (end != std::string::npos) {
                phrases.insert(line.substr(start + 1, end - start - 1));
                start = end + 1;
            }
            else {
                break;
            }
        }
    }
    return phrases;
}

std::vector<std::string> listFiles(const std::string& directory) {
    std::vector<std::string> files;
    DIR* dir = opendir(directory.c_str());
    if (dir) {
        struct dirent* entry;
        while ((entry = readdir(dir)) != nullptr) {
            if (entry->d_type == DT_REG) {
                std::string filename(entry->d_name);
                if (filename.size() > 3 && filename.substr(filename.size() - 3) == ".md") {
                    files.push_back(directory + "/" + filename);
                }
            }
        }
        closedir(dir);
    }
    return files;
}

int main(int argc, char* argv[]) {
    if (argc != 2) {
        std::cerr << "Usage: " << argv[0] << " <directory>" << std::endl;
        return 1;
    }

    std::string directory = argv[1];
    std::vector<std::string> mdFiles = listFiles(directory);
    std::set<std::string> allPhrases;

    for (const auto& file : mdFiles) {
        std::set<std::string> phrases = findCursivePhrases(file);
        allPhrases.insert(phrases.begin(), phrases.end());
    }

    std::ofstream outFile("emph.txt");
    for (const auto& phrase : allPhrases) {
        outFile << phrase << std::endl;
    }

    return 0;
}
